package com.mygame.dialogue.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygame.dialogue.MainGame;
import com.mygame.dialogue.core.Dialogue;
import com.mygame.dialogue.screens.GameScreen;
import com.mygame.dialogue.screens.InventoryScreen;
import com.mygame.dialogue.screens.MainMenuScreen;

import java.util.Map;

public class InputHandler {
    private final MainGame game;

    public InputHandler(MainGame game) {
        this.game = game;
    }

    // Обработка ввода в GameScreen
    public void handleGameScreenInput() {
        // Открытие инвентаря по нажатию I
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.getScreenManager().setScreen(InventoryScreen.class); // Переход к экрану инвентаря
        }

        Dialogue currentDialogue = game.getGameState().getDialogueManager().getCurrentDialogue();
        int choiceIndex = 1; // Начинаем с первого варианта (NUM_1)
        for (Map.Entry<Integer, String> entry : currentDialogue.getChoices().entrySet()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + choiceIndex - 1)) {
                int choiceId = entry.getKey(); // Получаем choiceId
                game.getGameState().getDialogueManager().nextDialogue(choiceId); // Переход к следующей реплике
                currentDialogue.triggerChoiceSelected(choiceId); // Вызываем событие с choiceId

                // Если это выбор "Вернуться в меню", переключаемся на MainMenuScreen
                if (choiceId == 0) {
                    game.getScreenManager().setScreen(MainMenuScreen.class); // Переход к главному меню
                }
                break; // Прерываем цикл после обработки выбора
            }
            choiceIndex++;
        }
    }
    // Обработка ввода в InventoryScreen
    public void handleInventoryScreenInput() {
        // Закрытие инвентаря по нажатию I
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.getScreenManager().setScreen(GameScreen.class); // Возврат к игровому экрану
        }
    }
    // Обработка ввода в MainMenuScreen
    public void handleMainMenuScreenInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.getScreenManager().setScreen(GameScreen.class); // Переход к игровому экрану
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit(); // Выход из игры
        }
    }
}
