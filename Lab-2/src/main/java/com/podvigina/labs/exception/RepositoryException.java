package com.podvigina.labs.exception;

/**
 * Ошибка при работе с базой данных.
 * Оборачивает SQLException, чтобы не глотать ошибку, а пробросить её выше.
 */
public class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
