package com.wilterson.audit;

import com.wilterson.entity.Person;
import com.wilterson.shared.PersonRowMapper;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcCursorItemReader;

@Slf4j
public class PersonItemReader extends JdbcCursorItemReader<Person> {

    private static final int MAX_ITEMS_PER_JOB_RUN = 10;

    public PersonItemReader(DataSource dataSource) {

        setName("PersonReader");
        setSql("select id, first_name, last_name, status from person where status = 'NEW'");
        setDataSource(dataSource);
        setMaxRows(0);
        setFetchSize(MAX_ITEMS_PER_JOB_RUN);
        setQueryTimeout(10000);
        setRowMapper(new PersonRowMapper());

        logDataRead(this);
    }

    private static void logDataRead(JdbcCursorItemReader<Person> itemReader) {

        if (!log.isDebugEnabled()) {
            return;
        }

        int counter = 0;
        ExecutionContext executionContext = new ExecutionContext();
        itemReader.open(executionContext);
        Object object = new Object();
        try {
            while (object != null) {
                object = itemReader.read();
                if (object instanceof Person person) {
                    log.debug("Read {}", person.firstName());
                    counter++;
                }
            }
        } catch (Exception exception) {
            log.error("Not able to log when reading Person", exception);
        }

        itemReader.close();
        log.debug("Read a total of {} objects in this iteration", counter);
    }
}
