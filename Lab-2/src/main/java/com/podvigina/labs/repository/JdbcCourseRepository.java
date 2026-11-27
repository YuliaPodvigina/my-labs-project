package com.podvigina.labs.repository;

import com.podvigina.labs.entity.CourseEntity;
import com.podvigina.labs.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Вся работа с JDBC находится только в этом классе.
 */
public class JdbcCourseRepository implements CourseRepository {

    private static final String INSERT =
            "INSERT INTO course (title, duration_hours) VALUES (?, ?) RETURNING id";
    private static final String SELECT_BY_ID =
            "SELECT id, title, duration_hours FROM course WHERE id = ?";
    private static final String SELECT_BY_TITLE =
            "SELECT id, title, duration_hours FROM course WHERE title = ?";
    private static final String SELECT_ALL =
            "SELECT id, title, duration_hours FROM course ORDER BY id";
    private static final String UPDATE =
            "UPDATE course SET title = ?, duration_hours = ? WHERE id = ?";
    private static final String DELETE_BY_ID =
            "DELETE FROM course WHERE id = ?";

    private final DataSource dataSource;

    public JdbcCourseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int save(CourseEntity course) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT)) {

            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getDurationHours());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new RepositoryException("База не вернула id для курса " + course);
                }
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось сохранить курс " + course, e);
        }
    }

    @Override
    public CourseEntity findById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось найти курс с id=" + id, e);
        }
    }

    @Override
    public CourseEntity findByTitle(String title) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_TITLE)) {

            statement.setString(1, title);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось найти курс с названием '" + title + "'", e);
        }
    }

    @Override
    public List<CourseEntity> findAll() {
        List<CourseEntity> courses = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                courses.add(mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось получить список курсов", e);
        }

        return courses;
    }

    @Override
    public boolean update(CourseEntity course) {
        if (course.getId() == null) {
            throw new IllegalArgumentException("Для обновления у курса должен быть id");
        }

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {

            statement.setString(1, course.getTitle());
            statement.setInt(2, course.getDurationHours());
            statement.setInt(3, course.getId());

            int updatedRows = statement.executeUpdate();
            return updatedRows > 0;
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось обновить курс " + course, e);
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Не удалось удалить курс с id=" + id, e);
        }
    }

    // Превращает текущую строку из ResultSet в объект CourseEntity
    private CourseEntity mapRow(ResultSet resultSet) throws SQLException {
        return new CourseEntity(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getInt("duration_hours")
        );
    }
}
