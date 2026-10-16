package com.podvigina.labs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

    // Обычный вспомогательный класс под объект из CSV
    static class Character {
        private int id;
        private String name;

        public Character(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        String inputPath = "characters.csv";
        String outputPath = "result.txt";

        // По заданию используем Deque (ArrayDeque)
        Deque<Character> deque = new ArrayDeque<>();

        // 1. Читаем данные из файла
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputPath));

            // Пропускаем шапку таблицы
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Если строка пустая, идем дальше
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Разбиваем по запятой
                String[] parts = line.split(",");

                if (parts.length >= 2) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();

                    // Кладем в конец очереди
                    deque.addLast(new Character(id, name));
                }
            }
            reader.close();
            System.out.println("Файл прочитан. Всего персонажей в очереди: " + deque.size());

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка парсинга числа: " + e.getMessage());
            return;
        }

        // 2 и 3. Обработка и сразу запись в файл
        // По заданию: удалять по одному с головы, потом с хвоста, чередуя
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(outputPath));
            writer.println("Order,Name"); // Заголовок для понятного вывода

            int order = 1;
            boolean fromHead = true; // флаг: true - берем с головы, false - с хвоста

            while (!deque.isEmpty()) {
                Character current;

                if (fromHead) {
                    current = deque.pollFirst(); // забираем первый элемент
                } else {
                    current = deque.pollLast();  // забираем последний элемент
                }

                if (current != null) {
                    writer.println(order + "," + current.getName());
                    order++;
                }

                // Меняем направление на следующее
                fromHead = !fromHead;
            }

            writer.close();
            System.out.println("Готово! Результат сохранен в " + outputPath);

        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + e.getMessage());
        }
    }
}