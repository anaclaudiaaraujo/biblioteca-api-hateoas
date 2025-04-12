package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaApplication {
    public static void main(String[] args) {
        //System.out.println("Hello, JAVA "+ System.getProperty("java.version"));
        SpringApplication.run(BibliotecaApplication.class, args);
    }
}