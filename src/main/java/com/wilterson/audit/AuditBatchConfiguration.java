package com.wilterson.audit;

import com.wilterson.entity.Customer;
import com.wilterson.entity.Person;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class AuditBatchConfiguration {

    private static int STEP_CHUNK_SIZE = 7;

    @Bean
    public JdbcCursorItemReader<Person> reader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Person>()
                .name("PersonReader")
                .sql("select id, first_name, last_name, status from person where status = 'NEW'")
                .dataSource(dataSource)
                .maxRows(0)
                .fetchSize(STEP_CHUNK_SIZE)
                .queryTimeout(10000)
                .rowMapper(new DataClassRowMapper<>(Person.class))
                .build();
    }

    @Bean
    public ToUpperCaseItemProcessor processor(JdbcTemplate jdbcTemplate) {
        return new ToUpperCaseItemProcessor(jdbcTemplate);
    }

    @Bean
    public JdbcBatchItemWriter<Customer> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .sql("insert into customer (id, first_name, last_name) values (:id, :firstName, :lastName)")
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
    public Step copyDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcCursorItemReader<Person> personItemReader,
            ToUpperCaseItemProcessor toUpperCaseItemProcessor, JdbcBatchItemWriter<Customer> customerItemWriter, JdbcTemplate jdbcTemplate) {

        return new StepBuilder("copyDataStep", jobRepository)
                .<Person, Customer>chunk(STEP_CHUNK_SIZE, transactionManager)
                .reader(personItemReader)
                .listener(new AuditReadListener())
                .processor(toUpperCaseItemProcessor)
                .writer(customerItemWriter)
                .listener(new LogStepExecutionListener())
                .faultTolerant()
                .skip(Exception.class)
                .listener(new AuditSkipListener(jdbcTemplate))
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
                .listener(new LogStepExecutionListener())
                .build();
    }
}
