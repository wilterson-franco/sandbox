package com.wilterson.beanaccess;

public enum LoggerName {

    /**
     * The paths passed in to the constructors are defined in the logback.xml file.
     */

    CONSOLE("com.wilterson.beanaccess"),
    FILE("FILE.com.wilterson.beanaccess"),
    FILE_ROLLING("ROLLING.com.wilterson.beanaccess");

    private final String logName;

    LoggerName(String logName) {
        this.logName = logName;
    }

    public String logName() {
        return logName;
    }
}
