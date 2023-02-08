package com.griddynamics.lidlbooking.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class BookingDto {

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    private String note;

    @Schema(name = "lengthOfStay", nullable = true)
    private int lengthOfStay;

}
