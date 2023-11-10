package com.wilterson;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataMigrationRunner implements ApplicationRunner {

    private JobLauncher jobLauncher;
    private Job personToCustomerJob;

    @Autowired
    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    @Autowired
    public void setPersonToCustomerJob(@Qualifier("personToCustomerJob") Job personToCustomerJob) {
        this.personToCustomerJob = personToCustomerJob;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        jobLauncher.run(personToCustomerJob, jobParameters);
    }
}