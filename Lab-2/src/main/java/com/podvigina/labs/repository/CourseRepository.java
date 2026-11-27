package com.podvigina.labs.repository;

import com.podvigina.labs.entity.CourseEntity;

import java.util.List;

public interface CourseRepository {

    // Create (возвращает id созданной записи)
    int save(CourseEntity course);

    // Read (возвращает null, если запись не найдена)
    CourseEntity findById(int id);

    // Read по второму полю сущности — title (возвращает null, если не найдено)
    CourseEntity findByTitle(String title);

    List<CourseEntity> findAll();

    // Update (ищет по id, возвращает false, если запись не найдена)
    boolean update(CourseEntity course);

    // Delete
    void deleteById(int id);
}
