package com.griddynamics.lidlbooking.actuator;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DBConfig {

    @Bean(name = "dataSource")
    @ConfigurationProperties("database1.datasource")
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSource2")
    @ConfigurationProperties("database2.datasource")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }
}
