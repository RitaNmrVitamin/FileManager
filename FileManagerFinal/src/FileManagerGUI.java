import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * В этом классе реализуется графический интерфейс пользователя (GUI) для управления копированием файлов и вывода информации о файлах в указанной директории
 * @author Safonov Anton
 * @version 1.0
 */
public class FileManagerGUI extends Application {

    /** Поле ввода для пути к исходному файлу */
    private TextField sourceFileField;
    /** Поле ввода для пути к целевой директории */
    private TextField targetDirectoryField;
    /** Текстовая область для вывода сообщений и информации о файлах */
    private TextArea outputArea;

    /**
     * Точка входа в приложение. Запускает приложение через вызов launch
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Метод, который инициализирует графический интерфейс
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Manager");

        // Поля для ввода пути к исходному файлу и целевой директории
        sourceFileField = new TextField();
        sourceFileField.setPromptText("Путь к исходному файлу");
        targetDirectoryField = new TextField();
        targetDirectoryField.setPromptText("Путь к целевой директории");

        // Кнопка для запуска копирования файла
        Button copyButton = new Button("Скопировать файл");
        copyButton.setOnAction(e -> {
            String sourceFilePath = sourceFileField.getText();
            String targetDirectoryPath = targetDirectoryField.getText();
            File sourceFile = new File(sourceFilePath);
            File targetDirectory = new File(targetDirectoryPath);
            copyFile(sourceFile, targetDirectory);
            printFilesSizeInDirectory(targetDirectory);
        });

        // Текстовая область для вывода сообщений и размеров файлов
        outputArea = new TextArea();
        outputArea.setPrefHeight(200);

        // Компоновка элементов интерфейса
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(sourceFileField, targetDirectoryField, copyButton, outputArea);

        Scene scene = new Scene(layout, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Реализация функции копирования файла

    /**
     * Метод для копирования файла
     * @param sourceFile
     * @param targetDirectory
     */
    public void copyFile(File sourceFile, File targetDirectory) {
        File destFile = new File(targetDirectory, sourceFile.getName());
        try {
            Files.copy(sourceFile.toPath(), destFile.toPath());
            outputArea.appendText("Файл скопирован успешно.\n");
        } catch (IOException e) {
            outputArea.appendText("Ошибка при копировании файла: " + e.getMessage() + "\n");
        }
    }

    // Реализация функции вывода занимаемого объема файлов в папке

    /**
     * Метод для вывода занимаемого объема файлов в директории
     * @param directory
     */
    public void printFilesSizeInDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    outputArea.appendText("Имя файла: " + file.getName() + ", Размер: " + file.length() + " байт\n");
                }
            }
        } else {
            outputArea.appendText("Ошибка при получении файлов в директории.\n");
        }
    }
}