package com.griddynamics.lidlbooking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Booking API"))
public class BookingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

}
