package com.appdynamics.cloud.modern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ServicesApplication {

	public static void main(String[] args) {
		
		//SpringApplication.run(ServicesApplication.class, args);
		
	    SpringApplication springApplication = new SpringApplication(ServicesApplication.class);
	    springApplication.addListeners(new ServicesApplicationListener());
	    springApplication.run(args);
	
	}

}
