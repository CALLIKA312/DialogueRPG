package com.mygame.dialogue.core;


import com.badlogic.gdx.utils.Array;
import com.mygame.dialogue.MainGame;


public class GameState {

    private final MainGame game;
    private int health;
    private int strength; // Добавим силу
    private Array<String> inventory;

    public GameState(MainGame game) {
        this.game = game;
        this.inventory = new Array<>();
        this.health = 100;
        this.strength = 10; // Начальное значение силы

        // Загрузка диалогов из JSON (теперь это делается через DialogueManager)
        game.getDialogueManager().loadDialoguesFromJSON("dialogues.json");
    }
   /* private void loadDialoguesFromCSV() {
        FileHandle file = Gdx.files.internal("dialogues.csv"); // Путь к файлу
        String fileContent = file.readString();

        try (CSVReader reader = new CSVReader(new StringReader(fileContent))) {
            String[] nextLine;
            boolean isHeader = true;

            // Читаем файл построчно
            while ((nextLine = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false; // Пропускаем заголовок
                    continue;
                }

                // Проверяем, что строка содержит достаточно колонок
                if (nextLine.length < 2) {
                    System.err.println("Ошибка: строка содержит недостаточно данных: " + String.join(",", nextLine));
                    continue; // Пропускаем эту строку
                }

                // Очищаем ID от лишних пробелов
                String idStr = nextLine[0].trim();
                int id;
                try {
                    id = Integer.parseInt(idStr); // Преобразуем ID в число
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: неверный формат ID в строке: " + idStr);
                    continue; // Пропускаем эту строку
                }

                // Очищаем текст от лишних пробелов
                String text = nextLine[1].trim();

                // Создаем диалог
                Dialogue dialogue = new Dialogue(id, text);

                // Добавляем варианты ответов (если колонки существуют)
                if (nextLine.length > 2 && !nextLine[2].trim().isEmpty()) {
                    try {
                        int choice1Id = Integer.parseInt(nextLine[2].trim());
                        String choice1Text = nextLine[3].trim();
                        dialogue.addChoice(choice1Id, choice1Text);
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка: неверный формат choice1_id в строке: " + nextLine[2]);
                    }
                }

                if (nextLine.length > 4 && !nextLine[4].trim().isEmpty()) {
                    try {
                        int choice2Id = Integer.parseInt(nextLine[4].trim());
                        String choice2Text = nextLine[5].trim();
                        dialogue.addChoice(choice2Id, choice2Text);
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка: неверный формат choice2_id в строке: " + nextLine[4]);
                    }
                }

                // Обработка событий (если колонка event существует)
                if (nextLine.length > 6 && !nextLine[6].trim().isEmpty()) {
                    String event = nextLine[6].trim();
                    dialogue.setOnChoiceSelected(choiceId -> processEvent(event));
                }

                // Добавляем диалог в менеджер
                dialogueManager.addDialogue(dialogue);
                System.out.println("Обработана строка: " + String.join(",", nextLine));
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Ошибка при чтении CSV: " + e.getMessage());
        }
    }
*/

    public  void processEvent(String event) {
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
                    int newHealth = getHealth() + healthChange;
                    if (newHealth < 0) newHealth = 0; // Здоровье не может быть меньше 0
                    setHealth(newHealth);
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

    // Метод для добавления предмета в инвентарь
    public boolean addItemToInventory(String item) {
        if (inventory.size < 6) { // Максимум 6 предметов
            inventory.add(item);
            return true;
        }
        return false;
    }


    // Геттеры и сеттеры

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


}
