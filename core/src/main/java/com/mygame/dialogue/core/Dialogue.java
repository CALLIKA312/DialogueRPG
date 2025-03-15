package com.mygame.dialogue.core;


import java.util.HashMap;
import java.util.Map;
import java.util.function.IntConsumer;

public class Dialogue {
    private int id;
    private String text;
    private Map<Integer, String> choices;
    private IntConsumer onChoiceSelected; // Событие при выборе с передачей choiceId

    public Dialogue(int id, String text) {
        this.id = id;
        this.text = text;
        this.choices = new HashMap<>();
    }

    public void addChoice(int nextId, String choiceText) {
        choices.put(nextId, choiceText);
    }

    // Перегрузка для лямбды без параметров
    public void setOnChoiceSelected(Runnable onChoiceSelected) {
        this.onChoiceSelected = choiceId -> onChoiceSelected.run();
    }

    // Перегрузка для лямбды с параметром
    public void setOnChoiceSelected(IntConsumer onChoiceSelected) {
        this.onChoiceSelected = onChoiceSelected;
    }

    public void triggerChoiceSelected(int choiceId) {
        if (onChoiceSelected != null) {
            onChoiceSelected.accept(choiceId);
        }
    }
    // Геттеры
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Map<Integer, String> getChoices() {
        return choices;
    }
}
