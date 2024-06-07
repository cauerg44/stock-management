package com.appfullstack.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("PASSWORD ENCODE = " + passwordEncoder.encode("app-2024"));
		
		boolean result = passwordEncoder.matches("app-2024", "$2a$10$QrDGMknxOAoy6zVSV23RaOS94rsIj/6VpG9drqHGrI9.AIkNysCh2");
		System.out.println("RESULT = " + result);
	}

}
