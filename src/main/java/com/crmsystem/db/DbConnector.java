package com.crmsystem.db;

import com.crmsystem.model.ApplicationRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DbConnector {
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String login = "alibek-home";
            String password = "root";
            String url = "jdbc:postgresql://localhost:5432/crm-system";

            connection = DriverManager.getConnection(url, login, password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ApplicationRequest> getAllApplications() {
        ArrayList<ApplicationRequest> applicationRequests = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from application_requests");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ApplicationRequest request = new ApplicationRequest();

                request.setId(resultSet.getLong("id"));
                request.setUserName(resultSet.getString("user_name"));
                request.setCourseName(resultSet.getString("course_name"));
                request.setCommentary(resultSet.getString("commentary"));
                request.setPhone(resultSet.getString("phone"));
                request.setHandled(resultSet.getBoolean("handled"));

                applicationRequests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationRequests;
    }

    public static void addApplication(ApplicationRequest applicationRequest) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO application_requests (user_name, course_name, commentary, phone, handled) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, applicationRequest.getUserName());
            statement.setString(2, applicationRequest.getCourseName());
            statement.setString(3, applicationRequest.getCommentary());
            statement.setString(4, applicationRequest.getPhone());
            statement.setBoolean(5, applicationRequest.isHandled());

            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApplicationRequest getApplicationById(Long id) {
        ApplicationRequest applicationRequest = new ApplicationRequest();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from application_requests WHERE id = ?");
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                applicationRequest.setId(resultSet.getLong("id"));
                applicationRequest.setUserName(resultSet.getString("user_name"));
                applicationRequest.setCourseName(resultSet.getString("course_name"));
                applicationRequest.setCommentary(resultSet.getString("commentary"));
                applicationRequest.setPhone(resultSet.getString("phone"));
                applicationRequest.setHandled(resultSet.getBoolean("handled"));

                statement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return applicationRequest;
    }

    public static void updateApplication(ApplicationRequest applicationRequest) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE application_requests " +
                            "SET user_name = ?, course_name = ?, commentary = ?, phone = ?, handled = ? " +
                            "WHERE id = ?"
            );

            statement.setString(1, applicationRequest.getUserName());
            statement.setString(2, applicationRequest.getCourseName());
            statement.setString(3, applicationRequest.getCommentary());
            statement.setString(4, applicationRequest.getPhone());
            statement.setBoolean(5, applicationRequest.isHandled());
            statement.setLong(6, applicationRequest.getId());

            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteApplication(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM application_requests WHERE id = ?"
            );
            statement.setLong(1, id);

            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
