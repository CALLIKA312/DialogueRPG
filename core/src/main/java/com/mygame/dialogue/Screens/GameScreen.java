package com.mygame.dialogue.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.dialogue.DialogueGame;
import com.mygame.dialogue.Dialogues.Dialogue;
import com.mygame.dialogue.Dialogues.DialogueManager;
import com.mygame.dialogue.GameState;

import java.util.Map;

public class GameScreen implements Screen {
    private final DialogueGame game;
    private final GameState gameState;
    private SpriteBatch batch;
    private BitmapFont font;
    private Viewport viewport;

    public GameScreen(DialogueGame game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
        // Инициализация viewport в конструкторе
        viewport = new FitViewport(800, 600);
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

        // Обработка ввода
        handleInput();
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

    private void handleInput() {
        // Открытие инвентаря по нажатию I
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.openInventory(); // Открываем инвентарь
        }

        Dialogue currentDialogue = gameState.getDialogueManager().getCurrentDialogue();
        int choiceIndex = 1; // Начинаем с первого варианта (NUM_1)
        for (Map.Entry<Integer, String> entry : currentDialogue.getChoices().entrySet()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + choiceIndex - 1)) {
                int choiceId = entry.getKey(); // Получаем choiceId
                gameState.getDialogueManager().nextDialogue(choiceId); // Переход к следующей реплике
                currentDialogue.triggerChoiceSelected(choiceId); // Вызываем событие с choiceId

                // Если это выбор "Вернуться в меню", переключаемся на MainMenuScreen
                if (choiceId == 0) {
                    game.setScreen(new MainMenuScreen(game));
                }
                break; // Прерываем цикл после обработки выбора
            }
            choiceIndex++;
        }
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
