package com.griddynamics.lidlbooking.api.dto.search;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchBookingByStartDateDto {

    private List<Long> seasonIdList;
    private LocalDate startDate;

}
