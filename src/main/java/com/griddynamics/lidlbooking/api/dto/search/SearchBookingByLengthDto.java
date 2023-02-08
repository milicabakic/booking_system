package com.griddynamics.lidlbooking.api.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchBookingByLengthDto {

    private List<Long> seasonIdList;
    private int lengthOfStay;

    //date ranges

}
