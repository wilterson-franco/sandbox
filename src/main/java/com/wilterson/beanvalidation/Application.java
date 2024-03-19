package com.wilterson.beanvalidation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }
}

@Component
class MyRunner implements CommandLineRunner {

    private final PersonService personService;

    public MyRunner(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(String... args) {

        try {
            personService.backgroundCheck(new Person(null, 4));
        } catch (ConstraintViolationException e) {
            System.out.println("<<< EXCEPTION >>>");
            return;
        }

        System.out.println("<<< NO CONSTRAINT VALIDATIONS >>>");
    }
}