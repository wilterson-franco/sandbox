package com.wilterson.beanaccess;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {SpringContext.class, UserServiceMock.class})
class BusinessWorkerTest {

    private TestLogAppender testLogAppender;

    @BeforeEach
    void setup() {
        testLogAppender = new TestLogAppender();
        testLogAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());

        Logger consoleLog = (Logger) LoggerFactory.getLogger(LoggerName.CONSOLE.logName());
        consoleLog.setLevel(Level.DEBUG);
        consoleLog.addAppender(testLogAppender);

        testLogAppender.start();
    }

    @Test
    void shouldLog() {

        // given
        BusinessWorker worker = new BusinessWorker();

        // when
        worker.generateLogs();

        // then
        assertThat(testLogAppender.countEventsForLogger(LoggerName.CONSOLE.logName())).isEqualTo(1);
    }
}