package com.trainingup.trainingupapp;

import com.trainingup.trainingupapp.tables.Course;
import com.trainingup.trainingupapp.tables.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class TrainingUpAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingUpAppApplication.class, args);
	}

	public static User createUser(String fname, String email, String lname, String type, String pass) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setFirstName(fname);
		newUser.setLastName(lname);
		newUser.setType(type);
		newUser.setPassword(pass);
		return newUser;
	}

	public static Course createCourse(String courseName, LocalDate start, LocalDate end, int cap, int actCap) {
		Course newCourse = new Course();
		newCourse.setActualCapacity(actCap);
		newCourse.setCapacity(cap);
		newCourse.setCourseName(courseName);
		newCourse.setStartDate(start);
		newCourse.setEndDate(end);
		newCourse.setProjectManager("Mirel");
		return newCourse;
	}
}
