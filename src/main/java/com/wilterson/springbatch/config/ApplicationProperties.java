package com.wilterson.springbatch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Batch batch = new Batch();

    public final Batch getBatch() {
        return batch;
    }

    public static class Batch {

        private String inputPath = "c:/demo/patient-batch-loader/data";

        public String getInputPath() {
            return inputPath;
        }

        public void setInputPath(String inputPath) {
            this.inputPath = inputPath;
        }
    }
}
