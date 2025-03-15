package com.mygame.dialogue.utils;

import com.badlogic.gdx.Screen;
import com.mygame.dialogue.DialogueGame;

public class ScreenManager {
    private final DialogueGame game;

    public ScreenManager(DialogueGame game) {
        this.game = game;
    }

    public Screen getScreen() {
        return game.getScreen();
    }

    public void setScreen(Screen screen) {
        game.setScreen(screen);
    }
}
