package com.wilterson.beanvalidation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Person(
        @NotBlank(message = "Name must not be blank")
        String name,

        @Min(value = 18, message = "Age should not be less than 18")
        int age) {

}
