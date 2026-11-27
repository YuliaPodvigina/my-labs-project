package com.podvigina.labs.entity;

public class CourseEntity {

    private final Integer id;

    private String title;

    private Integer durationHours;

    // Для нового курса, который ещё не сохранён в БД (id выдаст база)
    public CourseEntity(String title, Integer durationHours) {
        this(null, title, durationHours);
    }

    // Для курса, который уже есть в БД
    public CourseEntity(Integer id, String title, Integer durationHours) {
        this.id = id;
        this.title = title;
        this.durationHours = durationHours;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    @Override
    public String toString() {
        return "CourseEntity{id=" + id + ", title='" + title + "', durationHours=" + durationHours + "}";
    }
}
