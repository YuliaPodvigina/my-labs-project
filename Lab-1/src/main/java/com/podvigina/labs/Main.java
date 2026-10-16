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

public class Main {

    private static final String INPUT_PATH = "characters.csv";
    private static final String OUTPUT_PATH = "result.txt";

    public static void main(String[] args) {
        try {
            Deque<Character> deque = readCharacters(INPUT_PATH);
            System.out.println("Считано персонажей: " + deque.size());

            List<String> orderedNames = processDeque(deque);

            writeResults(OUTPUT_PATH, orderedNames);
            System.out.println("Результаты успешно записаны в: " + OUTPUT_PATH);

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    private static Deque<Character> readCharacters(String filePath) throws IOException {
        Deque<Character> deque = new ArrayDeque<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        deque.addLast(new Character(id, name));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }

        return deque;
    }

    private static List<String> processDeque(Deque<Character> deque) {
        List<String> result = new ArrayList<>();
        boolean takeFromHead = true;

        while (!deque.isEmpty()) {
            Character character;

            if (takeFromHead) {
                character = deque.pollFirst();
            } else {
                character = deque.pollLast();
            }

            if (character != null) {
                result.add(character.getName());
            }

            takeFromHead = !takeFromHead;
        }

        return result;
    }

    private static void writeResults(String filePath, List<String> names) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Order,Name");

            int order = 1;
            for (String name : names) {
                writer.println(order + "," + name);
                order++;
            }
        }
    }
}