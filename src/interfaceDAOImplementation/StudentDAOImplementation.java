/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceDAOImplementation;

import connection.ConnectionFactory;
import interfaceDAO.StudentDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Student;

/**
 *
 * @author Julian
 */
public class StudentDAOImplementation implements StudentDAO {

    Connection connection = ConnectionFactory.getConnection();

    //Constructor   
    public StudentDAOImplementation() {
    }

    // Create a new Student
    public void create(Student student) {

        PreparedStatement preparedStatement = null;

        try {
            String createQuery = "INSERT INTO STUDENT (student_id, first_name, last_name) VALUES(?,?,?)";
            preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setInt(1, student.getStudentId());
            preparedStatement.setString(2, student.getStudentFirstname());
            preparedStatement.setString(3, student.getStudentLastname());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    // Find a student by ID
    @Override
    public Student findById(int id) {

        Student student = null;
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = null;

        try {
            String selectIdQuery = "SELECT * FROM STUDENT WHERE STUDENT_ID= ?";
            preparedStatement = connection.prepareStatement(selectIdQuery);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            student = new Student();
            student.setStudentId(resultSet.getInt("student_id"));
            student.setStudentFirstname(resultSet.getString("first_name"));
            student.setStudentLastname(resultSet.getString("last_name"));
            resultSet.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return student;
    }

    // find all students
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();
        Student student = null;
        ResultSet resultSet;
        PreparedStatement preparedStatement;

        try {
            String selectAllQuery = "SELECT * FROM STUDENT ORDER BY STUDENT_ID";
            preparedStatement = connection.prepareStatement(selectAllQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setStudentFirstname(resultSet.getString("first_name"));
                student.setStudentLastname(resultSet.getString("last_name"));
                students.add(student);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return students;
    }

    // Update a student's info
    public void update(Student student) {

        PreparedStatement preparedStatement;

        try {
            String updateQuery = "UPDATE STUDENT SET FIRST_NAME = ?, LAST_NAME = ? WHERE STUDENT_ID = ?";
            //System.out.println("Query = " + updateQuery);
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, student.getStudentFirstname());
            preparedStatement.setString(2, student.getStudentLastname());
            preparedStatement.setInt(3, student.getStudentId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // delete a student
    public void delete(int id) {

        PreparedStatement preparedStatement;

        try {
            String deleteQuery = "DELETE FROM STUDENT WHERE STUDENT_ID =" + id;
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
