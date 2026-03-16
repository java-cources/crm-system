package com.crmsystem.db;

import com.crmsystem.model.ApplicationRequest;
import com.crmsystem.model.Course;

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

    private static ApplicationRequest mapApplicationRequest(ResultSet resultSet) throws Exception {
        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setId(resultSet.getLong("id"));
        applicationRequest.setUserName(resultSet.getString("user_name"));
        applicationRequest.setCommentary(resultSet.getString("commentary"));
        applicationRequest.setPhone(resultSet.getString("phone"));
        applicationRequest.setHandled(resultSet.getBoolean("handled"));
        applicationRequest.setCourseId(resultSet.getLong("course_id"));

        Course course = new Course();
        course.setId(resultSet.getLong("course_id"));
        course.setName(resultSet.getString("name"));
        course.setPrice(resultSet.getInt("price"));
        course.setDescription(resultSet.getString("description"));

        applicationRequest.setCourse(course);
        return applicationRequest;
    }

    public static ArrayList<ApplicationRequest> getAllApplications() {
        ArrayList<ApplicationRequest> applicationRequests = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from application_requests ar JOIN courses c on ar.course_id = c.id"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ApplicationRequest request = mapApplicationRequest(resultSet);
                applicationRequests.add(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationRequests;
    }

    public static ArrayList<ApplicationRequest> getUnhandledApplications() {
        ArrayList<ApplicationRequest> applicationRequests = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from application_requests ar INNER JOIN courses c on ar.course_id = c.id WHERE ar.handled = false");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ApplicationRequest request = mapApplicationRequest(resultSet);
                applicationRequests.add(request);
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationRequests;
    }

    public static ArrayList<ApplicationRequest> getHandledApplications() {
        ArrayList<ApplicationRequest> applicationRequests = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from application_requests ar INNER JOIN courses c on ar.course_id = c.id WHERE ar.handled = true");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ApplicationRequest request = mapApplicationRequest(resultSet);
                applicationRequests.add(request);
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return applicationRequests;
    }

    public static void addApplication(ApplicationRequest applicationRequest) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO application_requests (user_name, commentary, phone, handled, course_id) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, applicationRequest.getUserName());
            statement.setString(2, applicationRequest.getCommentary());
            statement.setString(3, applicationRequest.getPhone());
            statement.setBoolean(4, applicationRequest.isHandled());
            statement.setLong(5, applicationRequest.getCourseId());

            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ApplicationRequest getApplicationById(Long id) {
        ApplicationRequest applicationRequest = new ApplicationRequest();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from application_requests ar INNER JOIN courses c on ar.course_id = c.id WHERE ar.id = ?");
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                applicationRequest = mapApplicationRequest(resultSet);

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
                            "SET user_name = ?, commentary = ?, phone = ?, handled = ?, course_id = ? " +
                            "WHERE id = ?"
            );

            statement.setString(1, applicationRequest.getUserName());
            statement.setString(2, applicationRequest.getCommentary());
            statement.setString(3, applicationRequest.getPhone());
            statement.setBoolean(4, applicationRequest.isHandled());
            statement.setLong(5, applicationRequest.getCourseId());
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

    public static ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from courses"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course();

                course.setId(resultSet.getLong("id"));
                course.setName(resultSet.getString("name"));
                course.setPrice(resultSet.getInt("price"));
                course.setDescription(resultSet.getString("description"));

                courses.add(course);
            }

            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return courses;
    }
}
