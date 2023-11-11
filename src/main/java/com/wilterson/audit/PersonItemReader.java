package com.wilterson.audit;

import com.wilterson.entity.Person;
import com.wilterson.shared.PersonRowMapper;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;

@Slf4j
public class PersonItemReader extends JdbcCursorItemReader<Person> {

    private static final int MAX_ITEMS_PER_JOB_RUN = 10;

    public JdbcCursorItemReader<Person> getPersonJdbcCursorItemReader(DataSource dataSource) throws Exception {

        JdbcCursorItemReader<Person> itemReader = new JdbcCursorItemReaderBuilder<Person>()
                .name("PersonReader")
                .sql("select id, first_name, last_name, status from person where status = 'NEW'")
                .dataSource(dataSource)
                .maxRows(MAX_ITEMS_PER_JOB_RUN)
                .fetchSize(MAX_ITEMS_PER_JOB_RUN)
                .queryTimeout(10000)
                .rowMapper(new PersonRowMapper())
                .build();

        logDataRead(itemReader);

        return itemReader;
    }

    private static void logDataRead(JdbcCursorItemReader<Person> itemReader) throws Exception {

        if (!log.isDebugEnabled()) {
            return;
        }

        int counter = 0;
        ExecutionContext executionContext = new ExecutionContext();
        itemReader.open(executionContext);
        Object object = new Object();
        while (object != null) {
            object = itemReader.read();
            if (object instanceof Person person) {
                log.debug("Read {}", person.firstName());
                counter++;
            }
        }
        itemReader.close();
        log.debug("Read a total of {} objects in this iteration", counter);
    }
}
