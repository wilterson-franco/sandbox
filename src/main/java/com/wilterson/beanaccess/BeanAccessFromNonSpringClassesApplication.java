package com.wilterson.beanaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeanAccessFromNonSpringClassesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeanAccessFromNonSpringClassesApplication.class, args);

        new BusinessWorker().generateLogs();
    }
}
