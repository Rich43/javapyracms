package com.pynguins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pynguins.controllers")
public class PyracmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PyracmsApplication.class, args);
	}
}
