package com.course.social.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    public static void clearDataBase() {
        // auto close connection and preparedStatement
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/social", "postgres", "123");) {

            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("DELETE FROM message_likes").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("DELETE from message").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("DELETE FROM user_subscriptions").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("DELETE FROM user_role").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("DELETE FROM usr").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("INSERT into usr (id, activation_code, active, email, password, username)" +
                            " VALUES (12,'activation_code',true,'test_user@email.com'," +
                            "'$2a$08$VlAjnobIPU7hRFP5iDY/nuAUnld2HxjhEjr5gy2OkxPzIVRTh/H3K','testuserregister');").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("INSERT into user_role (user_id, roles) VALUES (12,'USER'), (12, 'ADMIN');").execute();


            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("INSERT into usr (id, activation_code, active, email, password, username)" +
                            " VALUES (13,'activation_code1',true,'test_user1@email.com'," +
                            "'$2a$08$VlAjnobIPU7hRFP5iDY/nuAUnld2HxjhEjr5gy2OkxPzIVRTh/H3K','testuserregister123');").execute();
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("INSERT into user_role (user_id, roles) VALUES (13,'USER');").execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addMessage(int id, String text) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/social", "postgres", "123")) {
            DriverManager.getConnection("jdbc:postgresql://localhost:5432/social", "postgres", "123")
                    .prepareStatement("INSERT into message (id, filename, tag, text, user_id) values (" + id + ",null,'auto','" + text + "',12)").execute();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
