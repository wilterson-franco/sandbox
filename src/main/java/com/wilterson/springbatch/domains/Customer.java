package com.wilterson.springbatch.domains;

import com.wilterson.springbatch.domains.enums.Gender;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;
    private Address address;
    private boolean foreign;
}