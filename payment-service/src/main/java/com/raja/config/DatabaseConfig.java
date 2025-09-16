package com.raja.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;


@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Getter
public class DatabaseConfig {

    private String url;
    private String driverClassName;
    private String username;
    private String password;


}
