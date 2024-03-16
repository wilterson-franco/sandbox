package com.wilterson.beanaccess;

public class BusinessWorker {

    private final AppLogger appLogger = new AppLogger(BusinessWorker.class.getName());

    public void generateLogs() {

        appLogger.infoUser("Some logs");

    }
}
