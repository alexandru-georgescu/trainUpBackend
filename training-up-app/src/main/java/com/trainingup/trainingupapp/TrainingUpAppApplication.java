package com.trainingup.trainingupapp;

import com.trainingup.trainingupapp.controller.UserController;
import com.trainingup.trainingupapp.threads.SmtpThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TrainingUpAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrainingUpAppApplication.class, args);
		SmtpThread thread = new SmtpThread();
		thread.start();
	}

}
