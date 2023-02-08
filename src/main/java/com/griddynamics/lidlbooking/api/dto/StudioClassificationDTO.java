package com.griddynamics.lidlbooking.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudioClassificationDTO {

    @NotNull
    private List<String> amenity;

}
