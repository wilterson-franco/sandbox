package com.wilterson.beanaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    private final Logger log;

    public AppLogger() {
        log = LoggerFactory.getLogger(LoggerName.CONSOLE.logName());
    }

    public AppLogger(String logName) {
        log = LoggerFactory.getLogger(logName);
    }

    public AppLogger(String logName, LoggerName loggerName) {

        if (loggerName == LoggerName.CONSOLE) {
            log = LoggerFactory.getLogger(logName);
        } else {
            log = LoggerFactory.getLogger(String.format("%s.%s", loggerName.logName(), logName));
        }
    }

    private UserService getUserService() {
        return SpringContext.getBean(UserService.class);
    }

    /**
     * Returns a message with reference to the currently authenticated (logged in) user.
     *
     * @param message
     */
    public void infoUser(String message) {

        User user = getUserService().getCurrentUser();
        if (user == null) {
            log.info(message);
            return;
        }

        // if here, then has a user object
        message = String.format("<%s %s> " + message, user.firstName(), user.lastName());

        log.info(message);
    }
}
