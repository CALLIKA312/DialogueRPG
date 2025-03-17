package com.mygame.dialogue.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.mygame.dialogue.MainGame;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ExampleTest {
    @BeforeAll
    public static void init() {
        // Инициализация HeadlessApplication
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ExampleTest.EmptyApplicationListener(), config);

        // Убедитесь, что Gdx.files настроен на правильный путь
        Gdx.files = new ExampleTest.HeadlessFiles();
    }
    @BeforeAll
    static void setupAll() {
        System.out.println("Настройка перед всеми тестами");
    }

    @BeforeEach
    void setup() {
        System.out.println("Настройка перед каждым тестом");
    }

    @Test
    @DisplayName("Проверка сложения двух чисел")
    void testAddition() {
        assertEquals(4, 2 + 2, "2 + 2 должно быть 4");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Очистка после каждого теста");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Очистка после всех тестов");
    }

    @Test
    void testAssertions() {
        String text = "Hello, JUnit!";
        assertNotNull(text, "Текст не должен быть null");
        assertEquals("Hello, JUnit!", text, "Текст должен совпадать");
        assertTrue(text.startsWith("Hello"), "Текст должен начинаться с 'Hello'");
    }

    @Test
    void testException() {
        MainGame game = new MainGame();
        DialogueManager manager = new DialogueManager(game);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.nextDialogue(999); // Несуществующий ID
        });

        assertEquals("Недопустимый выбор: 999", exception.getMessage());
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