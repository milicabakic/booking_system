package com.griddynamics.lidlbooking.api.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookingDto {

    private List<Long> seasonIdList;
    private LocalDate startDate;
    private LocalDate endDate;
    private int lengthOfStay;

}
