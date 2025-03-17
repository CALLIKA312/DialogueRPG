package com.mygame.dialogue.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mygame.dialogue.MainGame;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogueManager {
    private final MainGame game;
    private Map<Integer, Dialogue> dialogues;
    private int currentDialogueId;

    public DialogueManager(MainGame game) {
        this.game = game;
        this.dialogues = new HashMap<>();
        this.currentDialogueId = 0;
    }

    // Загрузка диалогов из JSON
    public void loadDialoguesFromJSON(String filePath) {
        FileHandle file = Gdx.files.internal(filePath);
        String fileContent = file.readString("UTF-8");

        Gson gson = new Gson();
        Type dialogueListType = new TypeToken<List<Dialogue>>() {
        }.getType();
        List<Dialogue> loadedDialogues = gson.fromJson(fileContent, dialogueListType);

        for (Dialogue dialogue : loadedDialogues) {
            addDialogue(dialogue);

            // Привязка событий к вариантам ответов
            for (Choice choice : dialogue.getChoices()) {
                if (choice.getEvent() != null && !choice.getEvent().isEmpty()) {
                    setOnChoiceSelected(dialogue.getId(), choice.getId(), choice.getEvent());
                }
            }
        }
    }

    // Добавляем диалог
    public void addDialogue(Dialogue dialogue) {
        dialogues.put(dialogue.getId(), dialogue);
    }

    // Получение текущего диалога
    public Dialogue getCurrentDialogue() {
        return dialogues.get(currentDialogueId);
    }

    // Переход к следующему диалогу
    public void nextDialogue(int choiceId) {
        if (dialogues.containsKey(choiceId)) {
            // Вызываем событие, если оно есть
            Dialogue currentDialogue = dialogues.get(currentDialogueId);
            if (currentDialogue != null) {
                currentDialogue.triggerChoiceSelected(choiceId);
            }
            currentDialogueId = choiceId;


        } else {
            throw new IllegalArgumentException("Недопустимый выбор: " + choiceId);
        }
    }

    // Привязка события к выбору
    public void setOnChoiceSelected(int dialogueId, int choiceId, String event) {
        Dialogue dialogue = dialogues.get(dialogueId);
        if (dialogue != null) {
            dialogue.setOnChoiceSelected(choiceId, (id) -> game.getGameState().processEvent(event));
        }
    }
}



