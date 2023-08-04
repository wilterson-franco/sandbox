package com.wilterson.functional;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Person {

    private String firstName;
    private String lastName;
    private int age;
}
