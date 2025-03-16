package com.mygame.dialogue.utils;

import com.badlogic.gdx.Screen;
import com.mygame.dialogue.MainGame;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager {
    private final MainGame game;

    private final Map<Class<? extends Screen>, Screen> screens;

    public ScreenManager(MainGame game) {
        this.game = game;
        this.screens = new HashMap<>();
    }

    // Получение экрана по его классу
    public <T extends Screen> T getScreen(Class<T> screenClass) {
        if (!screens.containsKey(screenClass)) {
            try {
                // Создаем экран, если он еще не был создан
                T screen = screenClass.getDeclaredConstructor(MainGame.class).newInstance(game);
                screens.put(screenClass, screen);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при создании экрана: " + screenClass.getSimpleName(), e);
            }
        }
        return screenClass.cast(screens.get(screenClass));
    }

    // Переход к экрану
    public void setScreen(Class<? extends Screen> screenClass) {
        Screen screen = getScreen(screenClass);
        game.setScreen(screen);
    }
}
