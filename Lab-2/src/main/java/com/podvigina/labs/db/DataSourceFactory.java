package com.podvigina.labs.db;

import com.podvigina.labs.config.DatabaseConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Создаёт пул соединений HikariCP по настройкам из конфига.
 */
public class DataSourceFactory {

    public HikariDataSource createDataSource(DatabaseConfig config) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setSchema(config.getSchema());
        hikariConfig.setMaximumPoolSize(config.getPoolSize());

        return new HikariDataSource(hikariConfig);
    }
}
