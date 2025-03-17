package com.mygame.dialogue;

import com.badlogic.gdx.Game;
import com.mygame.dialogue.core.DialogueManager;
import com.mygame.dialogue.core.GameState;
import com.mygame.dialogue.screens.MainMenuScreen;
import com.mygame.dialogue.utils.InputHandler;
import com.mygame.dialogue.utils.ScreenManager;


public class MainGame extends Game {
    private GameState gameState; // Состояние игры
    private ScreenManager screenManager;

    private DialogueManager dialogueManager;
    private InputHandler inputHandler;

    @Override
    public void create() {
        screenManager = new ScreenManager(this);
        screenManager.setScreen(MainMenuScreen.class); // Переход к главному меню
        dialogueManager = new DialogueManager(this);
        inputHandler = new InputHandler(this);
        gameState = new GameState(this); // Инициализируем состояние игры
    }


    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public DialogueManager getDialogueManager() {
        return dialogueManager;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}
