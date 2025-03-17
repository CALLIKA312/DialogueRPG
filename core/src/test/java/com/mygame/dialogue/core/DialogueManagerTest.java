package com.mygame.dialogue.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.mygame.dialogue.MainGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DialogueManagerTest {

    @BeforeAll
    public static void init() {
        // Инициализация HeadlessApplication
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new EmptyApplicationListener(), config);

        // Убедитесь, что Gdx.files настроен на правильный путь
        Gdx.files = new HeadlessFiles();
    }

    @Test
    public void testLoadDialoguesFromJSON() {
        // Arrange
        MainGame game = new MainGame();
        GameState gameState = new GameState(game);
        DialogueManager manager = new DialogueManager(game);

        // Act
        manager.loadDialoguesFromJSON("dialogues.json");

        // Assert
        Dialogue dialogue = manager.getCurrentDialogue();
        assertNotNull(dialogue, "Диалог не должен быть null");
        assertEquals(0, dialogue.getId(), "ID диалога должен быть 0");
        assertEquals("Привет! Ты готов начать приключение?", dialogue.getText(), "Текст диалога не совпадает");
    }

    @Test
    public void testNextDialogue() {
        // Arrange
        MainGame game = new MainGame();
        GameState gameState = new GameState(game);
        DialogueManager manager = new DialogueManager(game);
        manager.loadDialoguesFromJSON("dialogues.json");

        // Act
        manager.nextDialogue(1); // Переход к следующему диалогу с ID 1

        // Assert
        Dialogue nextDialogue = manager.getCurrentDialogue();
        assertNotNull(nextDialogue, "Следующий диалог не должен быть null");
        assertEquals(1, nextDialogue.getId(), "ID следующего диалога должен быть 1");
    }


    // Пустой ApplicationListener для HeadlessApplication
    private static class EmptyApplicationListener implements ApplicationListener {
        @Override
        public void create() {
        }

        @Override
        public void resize(int width, int height) {
        }

        @Override
        public void render() {
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void dispose() {
        }
    }

    // Кастомная реализация Files для HeadlessApplication
    private static class HeadlessFiles implements Files {
        @Override
        public FileHandle internal(String path) {
            // Указываем путь к файлу относительно корня проекта
            return new FileHandle("../assets/" + path);
        }

        @Override
        public FileHandle external(String path) {
            return null;
        }

        @Override
        public FileHandle absolute(String path) {
            return null;
        }

        @Override
        public FileHandle local(String path) {
            return null;
        }

        @Override
        public FileHandle getFileHandle(String path, FileType type) {
            return null;
        }

        @Override
        public FileHandle classpath(String path) {
            return null;
        }

        @Override
        public String getExternalStoragePath() {
            return null;
        }

        @Override
        public boolean isExternalStorageAvailable() {
            return false;
        }

        @Override
        public String getLocalStoragePath() {
            return null;
        }

        @Override
        public boolean isLocalStorageAvailable() {
            return false;
        }
    }
}