package com.wilterson.audit;

import com.wilterson.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.SkipListener;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class AuditSkipListener implements SkipListener<Person, Person> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void onSkipInRead(Throwable throwable) {
        // TODO: must move the row from PERSON to PERSON_SKIP table
    }
}
