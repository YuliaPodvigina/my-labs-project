package com.podvigina.labs.db;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * Запускает миграции Flyway из папки resources/db/migration.
 * Если таблицы ещё нет — Flyway её создаст, если уже есть — ничего не сделает.
 */
public class MigrationRunner {

    private static final Logger log = LoggerFactory.getLogger(MigrationRunner.class);

    public void migrate(DataSource dataSource, String schema) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schema)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
        log.info("Миграции базы данных выполнены");
    }
}
