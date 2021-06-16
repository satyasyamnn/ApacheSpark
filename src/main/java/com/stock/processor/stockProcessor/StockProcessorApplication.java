package com.stock.processor.stockProcessor;

import com.stock.processor.stockProcessor.configuration.ApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class StockProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockProcessorApplication.class, args);
    }
}
