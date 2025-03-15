package com.mygame.dialogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.dialogue.DialogueGame;
import com.mygame.dialogue.utils.ScreenManager;

public class MainMenuScreen implements Screen {
    private final DialogueGame game;
    private ScreenManager screenManager;
    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;

    public MainMenuScreen(DialogueGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/alagard-12px-unicode.fnt"));
        font.setColor(0, 0, 0, 1); // Черный цвет текста
        viewport = new FitViewport(800, 600);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1); // Белый фон
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin(); // Начинаем отрисовку

        // Отрисовка текста меню
        float textX = 50; // Отступ слева
        float textY = viewport.getWorldHeight() - 50; // Отступ сверху
        font.draw(batch, "Главное меню", textX, textY);

        // Кнопка "Начать игру"
        font.draw(batch, "1. Начать игру", textX, textY - 50);

        // Кнопка "Выйти"
        font.draw(batch, "2. Выйти", textX, textY - 100);

        batch.end(); // Завершаем отрисовку

        // Обработка ввода
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            game.setScreen(screenManager.getScreen()); // Используем существующий GameScreen
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            Gdx.app.exit(); // Выход из игры
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
