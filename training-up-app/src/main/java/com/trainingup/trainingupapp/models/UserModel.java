package com.trainingup.trainingupapp.models;

import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;

import javax.persistence.Transient;
import java.util.List;

public class UserModel {
    private long id;
    private String email;
    private String type;
    private String firstName;
    private String lastName;
    private String password;
    private List<Course> courses;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", courses=" + courses +
                '}';
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public User convertToUser() {
        User userModel = new User();
        userModel.setId(id);
        userModel.setType(type);
        userModel.setPassword(password);
        userModel.setLastName(lastName);
        userModel.setFirstName(firstName);
        userModel.setCourses(courses);
        userModel.setEmail(email);
        return userModel;
    }
}
