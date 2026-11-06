package com.podvigina.labs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.io.FileNotFoundException;

public class Main {

    private static final String INPUT_PATH = "characters.csv";
    private static final String OUTPUT_PATH = "result.txt";

    public static void main(String[] args) {
        Deque<Character> deque = readCharacters(INPUT_PATH);
        if (deque.isEmpty()) {
            System.err.println("Очередь пуста или файл не был прочитан. Завершение работы.");
            return;
        }

        System.out.println("Считано персонажей: " + deque.size());

        List<String> orderedNames = processDeque(deque);

        writeResults(OUTPUT_PATH, orderedNames);
    }

    private static Deque<Character> readCharacters(String filePath) {
        Deque<Character> deque = new ArrayDeque<>();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + filePath);
            return deque;
        }

        try (reader) {
            String line = reader.readLine(); // Пропуск заголовка
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.err.println("Некорректная структура строки " + lineNumber + ": " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    deque.addLast(new Character(id, name));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Ошибка парсинга id на строке " + lineNumber + ": '" + parts[0] + "' не является числом", e
                    );
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении данных из файла: " + e.getMessage());
        }

        return deque;
    }

    private static List<String> processDeque(Deque<Character> deque) {
        List<String> result = new ArrayList<>();
        boolean takeFromHead = true;

        while (!deque.isEmpty()) {
            Character character = takeFromHead ? deque.pollFirst() : deque.pollLast();

            if (character != null) {
                result.add(character.getName());
            }

            takeFromHead = !takeFromHead;
        }

        return result;
    }

    private static void writeResults(String filePath, List<String> names) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Order,Name");

            int order = 1;
            for (String name : names) {
                writer.println(order + "," + name);
                order++;
            }
            System.out.println("Результаты успешно записаны в: " + filePath);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл " + filePath + ": " + e.getMessage());

        }
    }
}