package com.griddynamics.lidlbooking.imports;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import static com.griddynamics.lidlbooking.commons.StringConstants.COUNTRY_TASK;

@Component
public abstract class CountryTaskFactory {

    @Lookup(value = COUNTRY_TASK)
    public abstract CountryReaderTask createTask();

}
