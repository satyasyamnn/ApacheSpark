package com.stock.processor.stockProcessor.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("db")
@Getter
@Setter
public class DatabaseConfiguration {
    private String userName;
    private String passWord;
    private String dbUrl;
    private String driver;
}
