package com.wilterson;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class PersonItemProcessor implements ItemProcessor<Person, Customer> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Customer process(final Person person) {

        // Update the status to 'PROCESSED' in the Person table
        jdbcTemplate.update("UPDATE Person SET STATUS = 'PROCESSED' WHERE ID = ?", person.id());

        final Long id = person.id();
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();

        final Customer customer = new Customer(id, firstName, lastName);

        log.info("Converting (" + person + ") into (" + customer + ")");

        return customer;
    }

}