package com.podvigina.labs.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Читает параметры подключения к БД из файла application.properties.
 */
public class DatabaseConfig {

    private final String url;
    private final String user;
    private final String password;
    private final String schema;
    private final int poolSize;

    public DatabaseConfig(String fileName) {
        Properties properties = new Properties();

        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalStateException("Файл настроек не найден: " + fileName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось прочитать файл настроек: " + fileName, e);
        }

        this.url = properties.getProperty("db.url");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");
        this.schema = properties.getProperty("db.schema");
        this.poolSize = Integer.parseInt(properties.getProperty("db.pool.size", "5"));
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSchema() {
        return schema;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
