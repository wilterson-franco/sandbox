package com.wilterson.beanvalidation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootTest(classes = {PersonService.class, MethodValidationPostProcessor.class})
class PersonServiceTest {

    @Autowired
    PersonService personService;

    @Test
    void whenValidAdult_thenNoExceptions() {

        // given
        var adult = new Person("Adult", 25);
        var personService = new PersonService();

        // when
        personService.backgroundCheck(adult);

        // then, it should not throw any exception
    }

    @Test
    void whenValidMinor_thenException() {

        // given
        var minor = new Person("Minor", 4);

        // when
        var exception = assertThrows(ConstraintViolationException.class, () -> personService.backgroundCheck(minor));

        // then
        assertThat(exception.getConstraintViolations()).hasSize(1);
        assertThat(exception).hasMessageContaining("backgroundCheck.person.age: Age should not be less than 18");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void whenInvalidValidPerson_thenException(String name) {

        // given
        var invalidPerson = new Person(name, 20);

        // when
        var exception = assertThrows(ConstraintViolationException.class, () -> personService.backgroundCheck(invalidPerson));

        // then
        assertThat(exception.getConstraintViolations()).hasSize(1);
        assertThat(exception).hasMessageContaining("backgroundCheck.person.name: Name must not be blank");
    }
}