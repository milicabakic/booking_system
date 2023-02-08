package com.griddynamics.lidlbooking.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HealthIndicatorConfig {

    @Bean("DBHealthIndicator")
    public HealthIndicator smartDBHealthIndicator(final @Qualifier("dataSource2") @Autowired() DataSource ds) {
        return new DataSourceHealthIndicator(ds, "SELECT 1 FROM databasechangeloglock");
    }
}
