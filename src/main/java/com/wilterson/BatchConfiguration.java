package com.wilterson;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class BatchConfiguration {

    @Bean
    public JdbcCursorItemReader<Person> reader(DataSource dataSource) throws Exception {

        JdbcCursorItemReader<Person> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("SELECT ID, FIRST_NAME, LAST_NAME from PERSON");
        itemReader.setRowMapper(new PersonRowMapper());
        itemReader.setMaxRows(10);
        itemReader.setFetchSize(10);
        itemReader.setQueryTimeout(10000);
        int counter = 0;
        ExecutionContext executionContext = new ExecutionContext();
        itemReader.open(executionContext);
        Object customerCredit = new Object();
        while (customerCredit != null) {
            customerCredit = itemReader.read();
            counter++;
        }

        itemReader.close();

        return itemReader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Customer> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .sql("INSERT INTO customer (id, first_name, last_name) VALUES (:id, :firstName, :lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job personToCustomerJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("personToCustomerJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
            DataSourceTransactionManager transactionManager,
            JdbcCursorItemReader<Person> reader,
            PersonItemProcessor processor,
            JdbcBatchItemWriter<Customer> writer) {

        return new StepBuilder("step1", jobRepository)
                .<Person, Customer>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
