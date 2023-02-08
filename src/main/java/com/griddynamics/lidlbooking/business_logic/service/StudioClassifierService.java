package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.StudioClass;

import java.util.List;
import java.util.Map;

public class StudioClassifierService {

    public static final String NO_CLASS = "None";
    private final Map<StudioClass, List<String>> studioClasses;


    public StudioClassifierService(final Map<StudioClass, List<String>> studioClasses) {
        this.studioClasses = studioClasses;
    }

    public String classifyStudio(final List<String> studioAmenities) {
        for (StudioClass studioClass : studioClasses.keySet()) {
            if (checkStudioClass(studioAmenities, studioClass)) {
                return studioClass.name();
            }
        }
        return NO_CLASS;
    }

    private boolean checkStudioClass(final List<String> studioAmenities, final StudioClass studioClass) {
        return studioAmenities.containsAll(studioClasses.get(studioClass));
    }

}
