package com.wilterson.beanvalidation;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class PersonService {

    public void backgroundCheck(@Valid Person person) {
        System.out.printf("Running background check for %s%n", person.name());
    }
}
