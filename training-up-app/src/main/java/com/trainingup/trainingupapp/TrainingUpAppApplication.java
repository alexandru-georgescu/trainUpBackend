package com.trainingup.trainingupapp;

import com.trainingup.trainingupapp.threads.SmtpThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TrainingUpAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(TrainingUpAppApplication.class, args);
		SmtpThread mailThread = new SmtpThread();
		mailThread.start();
	}

}
