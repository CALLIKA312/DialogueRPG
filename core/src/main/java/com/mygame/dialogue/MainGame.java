package com.mygame.dialogue;

import com.badlogic.gdx.Game;
import com.mygame.dialogue.core.GameState;
import com.mygame.dialogue.screens.MainMenuScreen;
import com.mygame.dialogue.utils.ScreenManager;


public class MainGame extends Game {
    private GameState gameState; // Состояние игры
    private ScreenManager screenManager;

    @Override
    public void create() {
        gameState = new GameState(); // Инициализируем состояние игры
        screenManager = new ScreenManager(this);
        screenManager.setScreen(MainMenuScreen.class); // Переход к главному меню
    }


    public ScreenManager getScreenManager() {
        return screenManager;
    }

    // Метод для получения состояния игры
    public GameState getGameState() {
        return gameState;
    }
}
