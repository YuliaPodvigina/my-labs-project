package com.podvigina.labs;

import com.podvigina.labs.config.DatabaseConfig;
import com.podvigina.labs.db.DataSourceFactory;
import com.podvigina.labs.db.MigrationRunner;
import com.podvigina.labs.entity.CourseEntity;
import com.podvigina.labs.repository.CourseRepository;
import com.podvigina.labs.repository.JdbcCourseRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        DatabaseConfig config = new DatabaseConfig("application.properties");

        try (HikariDataSource dataSource = new DataSourceFactory().createDataSource(config)) {
            new MigrationRunner().migrate(dataSource, config.getSchema());

            CourseRepository repository = new JdbcCourseRepository(dataSource);
            demo(repository);
        }
    }

    private static void demo(CourseRepository repository) {
        deleteIfExists(repository, "Основы Java");
        deleteIfExists(repository, "Базы данных");

        int javaId = repository.save(new CourseEntity("Основы Java", 72));
        int dbId = repository.save(new CourseEntity("Базы данных", 36));
        log.info("Сохранены курсы с id={} и id={}", javaId, dbId);

        log.info("findById({}): {}", javaId, repository.findById(javaId));
        log.info("findById(-1) (такого нет): {}", repository.findById(-1));

        log.info("findByTitle('Базы данных'): {}", repository.findByTitle("Базы данных"));
        log.info("findByTitle('Нет такого курса'): {}", repository.findByTitle("Нет такого курса"));

        log.info("findAll(): {}", repository.findAll());

        CourseEntity updatedCourse = new CourseEntity(javaId, "Основы Java", 80);
        log.info("update существующего курса: {}", repository.update(updatedCourse));
        log.info("повторный update (идемпотентность): {}", repository.update(updatedCourse));
        log.info("после update: {}", repository.findById(javaId));

        CourseEntity missingCourse = new CourseEntity(-1, "Несуществующий курс", 10);
        log.info("update несуществующего курса: {}", repository.update(missingCourse));

        repository.deleteById(dbId);
        repository.deleteById(dbId);
        log.info("после deleteById({}): findById = {}", dbId, repository.findById(dbId));

        log.info("findAll() в конце: {}", repository.findAll());
    }

    private static void deleteIfExists(CourseRepository repository, String title) {
        CourseEntity existing = repository.findByTitle(title);
        if (existing != null) {
            repository.deleteById(existing.getId());
        }
    }
}