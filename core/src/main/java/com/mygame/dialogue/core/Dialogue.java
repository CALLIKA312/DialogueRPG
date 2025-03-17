package com.mygame.dialogue.core;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

public class Dialogue {
    private int id;
    private String text;
    private List<Choice> choices; // Используем List<Choice> вместо Map<Integer, String>

    private transient Map<Integer, IntConsumer> choiceEvents; // Инициализируем в конструкторе

    public Dialogue() {
        this.choices = new ArrayList<>();
        this.choiceEvents = new HashMap<>(); // Инициализация
    }

    public Dialogue(int id, String text) {
        this.id = id;
        this.text = text;
        this.choices = new ArrayList<>();
        this.choiceEvents = new HashMap<>(); // Инициализация
    }


    // Добавление варианта ответа
    public void addChoice(int nextId, String choiceText, String event) {
        Choice choice = new Choice();
        choice.setId(nextId);
        choice.setText(choiceText);
        choice.setEvent(event);
        choices.add(choice);
    }

    // Привязка события к выбору
    public void setOnChoiceSelected(int choiceId, IntConsumer onChoiceSelected) {
        choiceEvents.put(choiceId, onChoiceSelected);
    }

    // Вызов события при выборе варианта
    public void triggerChoiceSelected(int choiceId) {
        if (choiceEvents.containsKey(choiceId)) {
            choiceEvents.get(choiceId).accept(choiceId);
        }
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
