package com.griddynamics.lidlbooking.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@ToString
public class SeasonDto {

    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate toDate;

    @NotNull
    @Min(1)
    private int pricePerNight;

}
