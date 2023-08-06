package com.wilterson.springbatch.configurations.batch;

import com.wilterson.springbatch.domains.Customer;
import com.wilterson.springbatch.models.CustomerInput;
import com.wilterson.springbatch.processors.CustomerItemProcessor;
import com.wilterson.springbatch.readers.CustomerItemReader;
import com.wilterson.springbatch.writters.CustomerItemWriter;
import com.wilterson.springbatch.configurations.ApplicationProperties;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class CustomerBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private CustomerItemReader customerItemReader;

    @Autowired
    private CustomerItemProcessor customerItemProcessor;

    @Autowired
    private CustomerItemWriter customerItemWriter;

    @Bean
    public Step importCustomersStep() {
        return stepBuilderFactory.get("STEP-CUSTOMERS-01")
                // defines step input and output domains and chunk size
                .<CustomerInput, Customer>chunk(10)
                // defines the reader
                .reader(customerItemReader)
                // defines the processor
                .processor(customerItemProcessor)
                // defines the writer
                .writer(customerItemWriter)
                .build();
    }

    @Bean
    public Job importCustomersJob() {
        return jobBuilderFactory.get("JOB-IMPORT-CUSTOMER")
                // defines the step flow. You can add more steps in a Job by using next() method
                .flow(importCustomersStep())
                // end configuration
                .end()
                .build();
    }
}
