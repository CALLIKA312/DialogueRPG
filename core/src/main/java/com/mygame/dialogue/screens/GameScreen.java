package com.mygame.dialogue.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.dialogue.MainGame;
import com.mygame.dialogue.core.Dialogue;
import com.mygame.dialogue.utils.InputHandler;

import java.util.Map;

public class GameScreen implements Screen {
    private final MainGame game;
    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;
    private InputHandler inputHandler;

    public GameScreen(MainGame game) {
        this.game = game;
        // Инициализация viewport в конструкторе
        viewport = new FitViewport(800, 600);
        this.inputHandler = new InputHandler(game); // Инициализация InputHandler
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/alagard-12px-unicode.fnt"));
        font.setColor(0, 0, 0, 1); // Черный цвет текста
    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(1, 1, 1, 1); // Белый фон
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновление viewport
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Начинаем отрисовку
        batch.begin();

        // Отрисовка текущего диалога
        Dialogue currentDialogue = game.getGameState().getDialogueManager().getCurrentDialogue();
        String wrappedText = wrapText(currentDialogue.getText(), viewport.getWorldWidth() - 100);

        // Используем GlyphLayout для измерения высоты текста
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, wrappedText);

        // Отрисовка текста реплики
        font.draw(batch, wrappedText, 50, viewport.getWorldHeight() - 50);

        // Отрисовка вариантов ответа
        float choiceY = viewport.getWorldHeight() - 50 - layout.height - 20; // Смещаем варианты вниз
        int choiceIndex = 1; // Нумерация вариантов
        for (Map.Entry<Integer, String> entry : currentDialogue.getChoices().entrySet()) {
            font.draw(batch, choiceIndex + ". " + entry.getValue(), 50, choiceY);
            choiceY -= 30; // Отступ между вариантами
            choiceIndex++;
        }

        // Подсказка для открытия инвентаря
        font.draw(batch, "Нажми I, чтобы открыть инвентарь", 50, 50);

        // Завершаем отрисовку
        batch.end();

        // Обработка ввода через InputHandler
        inputHandler.handleGameScreenInput();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    private String wrapText(String text, float maxWidth) {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        GlyphLayout layout = new GlyphLayout();

        // Рассчитываем ширину пробела
        layout.setText(font, " ");
        float spaceWidth = layout.width;

        float lineWidth = 0;

        for (String word : words) {
            // Рассчитываем ширину слова
            layout.setText(font, word);
            float wordWidth = layout.width;

            // Если слово не умещается в текущую строку, переносим на новую
            if (lineWidth + wordWidth > maxWidth) {
                wrappedText.append("\n");
                lineWidth = 0;
            }

            // Добавляем слово к текущей строке
            wrappedText.append(word).append(" ");
            lineWidth += wordWidth + spaceWidth;
        }

        return wrappedText.toString();
    }
}
