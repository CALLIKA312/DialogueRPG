package com.mygame.dialogue;

import com.badlogic.gdx.Game;
import com.mygame.dialogue.screens.GameScreen;
import com.mygame.dialogue.screens.InventoryScreen;
import com.mygame.dialogue.screens.MainMenuScreen;
import com.mygame.dialogue.core.GameState;
import com.mygame.dialogue.utils.ScreenManager;


public class DialogueGame extends Game {
    private GameState gameState; // Состояние игры
    private GameScreen gameScreen;

    private ScreenManager screenManager;

    @Override
    public void create() {
        gameState = new GameState(); // Инициализируем состояние игры
        gameScreen = new GameScreen(this, gameState); // Передаем состояние в GameScreen
        screenManager.setScreen(new MainMenuScreen(this));
    }



    // Метод для открытия инвентаря
    public void openInventory() {
        setScreen(new InventoryScreen(this, gameState));
    }

    // Метод для возврата к игровому экрану
    public void returnToGameScreen() {
        setScreen(gameScreen); // Используем существующий GameScreen
    }

    // Метод для получения состояния игры
    public GameState getGameState() {
        return gameState;
    }
}
