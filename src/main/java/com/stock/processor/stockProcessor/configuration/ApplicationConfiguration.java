package com.stock.processor.stockProcessor.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spark.app")
@Getter
@Setter
public class ApplicationConfiguration {
    private String applicationName;
    private String master;
    private String inputPath;
    private String inputFileFormat;
    private String outputPath;
}
