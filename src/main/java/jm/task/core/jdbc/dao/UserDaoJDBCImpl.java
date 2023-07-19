package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        String createSQLQuery = """
                CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(128) NOT NULL,
                last_name VARCHAR(128) NOT NULL,
                age SMALLINT NOT NULL
                );
                """;

        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            statement.execute(createSQLQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropSQLQuery = """
                DROP TABLE IF EXISTS users
                """;
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            statement.execute(dropSQLQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveSQLQuerry = """
                INSERT INTO users (name, last_name, age)
                VALUES (?, ?, ?)
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(saveSQLQuerry)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с именем " + name + " добавлен в БД");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserSQLQuery = """
                DELETE FROM users
                WHERE id = ?
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(removeUserSQLQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String getAllUsersSQLQuery = """
                SELECT *
                FROM users
                """;
        List<User> users = new ArrayList<>();
        try (var connection = Util.open();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(getAllUsersSQLQuery);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String last_name = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");
                User user = new User(name, last_name, age);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersSQLQuery = """
                DELETE FROM users
                """;
        try (var connection = Util.open();
             var preparedStatement = connection.prepareStatement(cleanUsersSQLQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
