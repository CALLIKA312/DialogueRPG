package com.mygame.dialogue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Array;
import com.mygame.dialogue.Screens.GameScreen;
import com.mygame.dialogue.Screens.InventoryScreen;
import com.mygame.dialogue.Screens.MainMenuScreen;



public class DialogueGame extends Game {
    private GameState gameState; // Состояние игры
    private GameScreen gameScreen;
    private MainMenuScreen mainMenuScreen;

    @Override
    public void create() {
        gameState = new GameState(); // Инициализируем состояние игры
        gameScreen = new GameScreen(this, gameState); // Передаем состояние в GameScreen
        mainMenuScreen = new MainMenuScreen(this); // Главное меню
        setScreen(mainMenuScreen); // Устанавливаем стартовый экран
    }

    public GameScreen getGameScreen() {
        return gameScreen;
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
