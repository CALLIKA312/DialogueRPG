package com.mygame.dialogue.core;

import java.util.HashMap;
import java.util.Map;

public class DialogueManager {
    private Map<Integer, Dialogue> dialogues; // Все диалоги
    private int currentDialogueId; // Текущая реплика

    public DialogueManager() {
        this.dialogues = new HashMap<>();
        this.currentDialogueId = 0; // Начинаем с первой реплики
    }

    // Добавляем диалог
    public void addDialogue(Dialogue dialogue) {
        dialogues.put(dialogue.getId(), dialogue);
    }

    // Получаем текущую реплику
    public Dialogue getCurrentDialogue() {
        return dialogues.get(currentDialogueId);
    }

    // Переход к следующей реплике
    public void nextDialogue(int choiceId) {
        System.out.println("Переход к диалогу с id=" + choiceId); // Отладочный вывод
        if (dialogues.containsKey(choiceId)) {
            currentDialogueId = choiceId; // Обновляем текущую реплику
        } else {
            throw new IllegalArgumentException("Недопустимый выбор: " + choiceId);
        }
    }

    // Сброс диалога
    public void reset() {
        currentDialogueId = 0; // Возвращаемся к началу
    }
}



