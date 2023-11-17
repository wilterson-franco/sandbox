package com.wilterson.audit;

import com.wilterson.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;

@Slf4j
public class AuditReadListener implements ItemReadListener<Person> {

    public void afterRead(Person person) {

        if (!log.isDebugEnabled()) {
            return;
        }
        log.debug("Lister - Read {}", person.firstName());
    }

    public void onReadError(Person person) {
        log.error("Error reading {}", person.firstName());
    }
}
