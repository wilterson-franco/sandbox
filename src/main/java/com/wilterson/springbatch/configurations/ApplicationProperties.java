package com.wilterson.springbatch.configurations;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class ApplicationProperties {

    @Value("${batch.input.file-name}")
    private String inputFile;

    @Value("${batch.output.americans.file-name}")
    private String americansFile;
}