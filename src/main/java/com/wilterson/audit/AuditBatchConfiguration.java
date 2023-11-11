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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing
public class AuditBatchConfiguration {

    @Bean
    public JdbcCursorItemReader<Person> reader(DataSource dataSource) throws Exception {
        PersonItemReader itemReader = new PersonItemReader();
        return itemReader.getPersonJdbcCursorItemReader(dataSource);
    }

    @Bean
    public PersonItemProcessor processor(JdbcTemplate jdbcTemplate) {
        return new PersonItemProcessor(jdbcTemplate);
    }

    @Bean
    public JdbcBatchItemWriter<Customer> writer(DataSource dataSource) {
        CustomerItemWriter itemWriter = new CustomerItemWriter();
        return itemWriter.getItemWriter(dataSource);
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
    public Step copyDataStep(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcCursorItemReader<Person> reader,
            PersonItemProcessor processor, JdbcBatchItemWriter<Customer> writer) {

        return new StepBuilder("copyDataStep", jobRepository)
                .<Person, Customer>chunk(2, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new LogStepExecutionListener())
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
