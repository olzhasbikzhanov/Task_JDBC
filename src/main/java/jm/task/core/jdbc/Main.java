package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userMain = new UserServiceImpl();

        userMain.createUsersTable();

        userMain.saveUser("Boris","Johnson", (byte) 59);
        userMain.saveUser("Annalena","Baerbock", (byte) 42);
        userMain.saveUser("Olaf","Scholz", (byte) 65);
        userMain.saveUser("Emmanuel","Macron", (byte) 45);

        userMain.getAllUsers();

        userMain.removeUserById(2);

        userMain.cleanUsersTable();

        userMain.dropUsersTable();
    }
}
