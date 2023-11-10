package com.wilterson;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableBatchProcessing
@SpringBootApplication
public class FirstSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringBatchApplication.class, args);
    }

}
