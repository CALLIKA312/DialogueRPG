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
import com.mygame.dialogue.core.GameState;

public class InventoryScreen implements Screen {
    private final DialogueGame game;
    private final GameState gameState; // Состояние игры
    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;

    public InventoryScreen(DialogueGame game, GameState gameState) {
        this.game = game;
        this.gameState = gameState; // Используем переданное состояние
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

        // Отрисовка заголовка
        float textX = 50; // Отступ слева
        float textY = viewport.getWorldHeight() - 50; // Отступ сверху
        font.draw(batch, "Инвентарь", textX, textY);

        // Отрисовка характеристик
        font.draw(batch, "Здоровье: " + gameState.getHealth(), textX, textY - 50);
        font.draw(batch, "Сила: " + gameState.getStrength(), textX, textY - 100);

        // Отрисовка инвентаря
        float inventoryY = textY - 150;
        for (int i = 0; i < 6; i++) { // Максимум 6 ячеек
            String item = (i < gameState.getInventory().size) ? gameState.getInventory().get(i) : "[Пусто]";
            font.draw(batch, (i + 1) + ". " + item, textX, inventoryY);
            inventoryY -= 30; // Отступ между предметами
        }

        // Подсказка для закрытия инвентаря
        font.draw(batch, "Нажми I, чтобы закрыть инвентарь", textX, 50);

        batch.end(); // Завершаем отрисовку

        // Закрытие инвентаря по нажатию I
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.returnToGameScreen(); // Возврат к игровому экрану
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
