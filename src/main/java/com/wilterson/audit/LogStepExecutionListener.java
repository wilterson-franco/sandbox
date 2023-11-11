package com.wilterson.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class LogStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

        if (log.isDebugEnabled()) {
            log.debug("Before step execution");
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if (log.isDebugEnabled()) {
            log.debug("After step execution");
        }
        return ExitStatus.COMPLETED;
    }
}
