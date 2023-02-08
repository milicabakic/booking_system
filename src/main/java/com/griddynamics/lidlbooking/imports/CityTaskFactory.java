package com.griddynamics.lidlbooking.imports;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import static com.griddynamics.lidlbooking.commons.StringConstants.CITY_TASK;

@Component
public abstract class CityTaskFactory {

    @Lookup(value = CITY_TASK)
    public abstract CityReaderTask createTask();

}
