package com.wilterson;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public JdbcCursorItemReader<Person> reader(DataSource dataSource) throws Exception {

        JdbcCursorItemReader<Person> itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("select id, first_name, last_name, status from person where status = 'NEW'");
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
    public PersonItemProcessor processor(JdbcTemplate jdbcTemplate) {
        return new PersonItemProcessor(jdbcTemplate);
    }

    @Bean
    public JdbcBatchItemWriter<Customer> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .sql("insert into customer (id, first_name, last_name) VALUES (:id, :firstName, :lastName)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job personToCustomerJob(JobRepository jobRepository, @Qualifier("copyDataStep") Step copyDataStep,
            @Qualifier("deleteFromSource") Step deleteFromSource, JobCompletionNotificationListener listener) {
        return new JobBuilder("personToCustomerJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(copyDataStep)
                .next(deleteFromSource)
                .build();
    }

    @Bean
    @Qualifier("copyDataStep")
    public Step copyDataStep(JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JdbcCursorItemReader<Person> reader,
            PersonItemProcessor processor,
            JdbcBatchItemWriter<Customer> writer) {

        return new StepBuilder("copyDataStep", jobRepository)
                .<Person, Customer>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    @Qualifier("deleteFromSource")
    public Step deletePersonDataStep(JobRepository jobRepository, JdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager) {

        Tasklet tasklet = (contribution, chunkContext) -> {
            jdbcTemplate.update("DELETE FROM person WHERE status = 'PROCESSED'");
            return RepeatStatus.FINISHED;
        };

        return new StepBuilder("deleteFromSource", jobRepository)
                .tasklet(tasklet, transactionManager)
                .build();
    }
}
