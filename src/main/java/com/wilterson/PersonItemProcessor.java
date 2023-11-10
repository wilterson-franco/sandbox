package com.wilterson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person, Customer> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Customer process(final Person person) {
        final Long id = person.id();
        final String firstName = person.firstName().toUpperCase();
        final String lastName = person.lastName().toUpperCase();

        final Customer customer = new Customer(id, firstName, lastName);

        log.info("Converting (" + person + ") into (" + customer + ")");

        return customer;
    }

}