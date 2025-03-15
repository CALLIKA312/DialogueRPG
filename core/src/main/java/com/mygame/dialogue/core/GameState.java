package com.mygame.dialogue.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class GameState {
    private int health;
    private int strength; // Добавим силу
    private Array<String> inventory;
    private DialogueManager dialogueManager;

    public GameState() {
        this.inventory = new Array<>();
        this.health = 100;
        this.strength = 10; // Начальное значение силы
        this.dialogueManager = new DialogueManager();

        // Загрузка диалогов из CSV
        loadDialoguesFromCSV();
    }


    private void loadDialoguesFromCSV() {
        FileHandle file = Gdx.files.internal("dialogues.csv"); // Путь к файлу
        String fileContent = file.readString();
        String[] lines = fileContent.split("\\r?\\n"); // Разделяем на строки

        // Пропускаем заголовок (первую строку)
        for (int i = 1; i < lines.length; i++) {
            String[] row = lines[i].split(","); // Разделяем строку на колонки

            // Проверяем, что строка содержит достаточно колонок
            if (row.length < 2) {
                System.err.println("Ошибка: строка " + i + " содержит недостаточно данных: " + lines[i]);
                continue; // Пропускаем эту строку
            }

            // Очищаем ID от лишних пробелов
            String idStr = row[0].trim();
            int id;
            try {
                id = Integer.parseInt(idStr); // Преобразуем ID в число
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: неверный формат ID в строке " + i + ": " + idStr);
                continue; // Пропускаем эту строку
            }

            String text = row[1].trim(); // Очищаем текст от лишних пробелов

            Dialogue dialogue = new Dialogue(id, text);

            // Добавляем варианты ответов (если колонки существуют)
            if (row.length > 2 && !row[2].trim().isEmpty()) {
                try {
                    int choice1Id = Integer.parseInt(row[2].trim());
                    String choice1Text = row[3].trim();
                    dialogue.addChoice(choice1Id, choice1Text);
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: неверный формат choice1_id в строке " + i + ": " + row[2]);
                }
            }

            if (row.length > 4 && !row[4].trim().isEmpty()) {
                try {
                    int choice2Id = Integer.parseInt(row[4].trim());
                    String choice2Text = row[5].trim();
                    dialogue.addChoice(choice2Id, choice2Text);
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: неверный формат choice2_id в строке " + i + ": " + row[4]);
                }
            }

            // Обработка событий (если колонка event существует)
            if (row.length > 6 && !row[6].trim().isEmpty()) {
                String event = row[6].trim();
                dialogue.setOnChoiceSelected(choiceId -> processEvent(event));
            }

            dialogueManager.addDialogue(dialogue);
            System.out.println("Обработана строка: " + lines[i]);
        }
    }

    private void processEvent(String event) {
        if (event == null || event.isEmpty()) return;

        // Разделяем события по точке с запятой
        String[] parts = event.split(";");
        for (String part : parts) {
            // Разделяем ключ и значение
            String[] keyValue = part.split(":");
            if (keyValue.length < 2) continue; // Пропускаем некорректные события

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            // Обрабатываем событие
            switch (key) {
                case "health":
                    int healthChange = Integer.parseInt(value);
                    setHealth(getHealth() + healthChange);
                    System.out.println("Здоровье изменено на " + healthChange + ". Текущее здоровье: " + getHealth());
                    break;
                case "strength":
                    int strengthChange = Integer.parseInt(value);
                    setStrength(getStrength() + strengthChange);
                    System.out.println("Сила изменена на " + strengthChange + ". Текущая сила: " + getStrength());
                    break;
                case "item":
                    addItemToInventory(value);
                    System.out.println("Добавлен предмет: " + value);
                    break;
                default:
                    System.err.println("Неизвестное событие: " + key);
                    break;
            }
        }
        System.out.println("Обработано событие: " + event);
    }


    // Геттеры и сеттеры
    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }

    public Array<String> getInventory() {
        return inventory;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    // Метод для добавления предмета в инвентарь
    public boolean addItemToInventory(String item) {
        if (inventory.size < 6) { // Максимум 6 предметов
            inventory.add(item);
            return true;
        }
        return false;
    }
}
