package peaksoft.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    public UserDaoJdbcImpl() {
    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE users (
                id serial PRIMARY KEY,
                first_name varchar(255),
                last_name varchar(255),
                age int 
                )
                """;
        try (Statement statement = connection.createStatement()){
            statement.execute(sql);
            System.out.println("Created users table");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "drop table users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table seccessfully dropped");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users (first_name, last_name, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User seccessfully inserted");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    public void removeUserById(long id) {
        String sql = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User seccessfully deleted");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        String sql = "select * from users";
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(new User(resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        (byte) resultSet.getInt("age")

                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }


    public void cleanUsersTable() {
        String sql = "drop table if exists users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table dropped");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}