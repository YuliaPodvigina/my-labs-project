package com.podvigina.labs;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Пути к файлам
        String inputFile = "characters.csv";
        String outputFile = "deque_result.txt";

        // Коллекция Deque (ArrayDeque)
        Deque<String> charactersDeque = new ArrayDeque<>();

        // ============ ЭТАП 1. ЧТЕНИЕ ============
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8))) {

            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                // Пропускаем заголовок
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Пропускаем пустые строки
                if (line.trim().isEmpty()) continue;

                // Разбиваем строку CSV по запятой
                String[] parts = line.split(",");

                // Имя — второй столбец (индекс 1)
                if (parts.length > 1) {
                    String name = parts[1].trim();
                    charactersDeque.add(name); // добавляем в хвост очереди
                }
            }
            System.out.println("Загружено персонажей: " + charactersDeque.size());

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return;
        }

        // ============ ЭТАП 2. ОБРАБОТКА ============
        // Чередуем извлечение: сначала с головы, потом с хвоста
        List<String> extractedOrder = new ArrayList<>();
        boolean fromHead = true; // флаг: true — с головы, false — с хвоста

        while (!charactersDeque.isEmpty()) {
            String name;
            if (fromHead) {
                name = charactersDeque.pollFirst(); // берём с головы
                System.out.println("С головы: " + name);
            } else {
                name = charactersDeque.pollLast();  // берём с хвоста
                System.out.println("С хвоста: " + name);
            }
            extractedOrder.add(name);
            fromHead = !fromHead; // меняем сторону
        }

        // ============ ЭТАП 3. ЗАПИСЬ ============
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {

            bw.write("Порядок извлечения персонажей из Deque:");
            bw.newLine();
            bw.write("========================================");
            bw.newLine();

            int index = 1;
            for (String name : extractedOrder) {
                bw.write(index + ". " + name);
                bw.newLine();
                index++;
            }

            bw.newLine();
            bw.write("Всего извлечено: " + extractedOrder.size());
            bw.newLine();

            System.out.println("\nРезультат записан в файл: " + outputFile);

        } catch (IOException e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}