package com.wilterson.audit;

import com.wilterson.entity.Customer;
import com.wilterson.entity.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@RequiredArgsConstructor
public class ToUpperCaseItemProcessor implements ItemProcessor<Person, Customer> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Customer process(final Person person) {

        // Update the status to 'PROCESSED' in the Person table
        jdbcTemplate.update("UPDATE Person SET STATUS = 'PROCESSED' WHERE ID = ?", person.id());

        final Long id = person.id();
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();

        final Customer customer = new Customer(id, firstName, lastName);

        log.debug("Converting (" + person + ") into (" + customer + ")");

        return customer;
    }

}