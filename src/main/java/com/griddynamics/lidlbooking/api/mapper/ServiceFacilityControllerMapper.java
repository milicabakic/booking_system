package com.griddynamics.lidlbooking.api.mapper;

import com.griddynamics.lidlbooking.api.dto.ServiceFacilityDTO;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceFacilityControllerMapper {

    ServiceFacility serviceFacilityDTOToServiceFacility(ServiceFacilityDTO serviceFacilityDTO);

    List<ServiceFacilityDTO> serviceFacilityListToServiceFacilityDTOList(List<ServiceFacility> serviceFacilities);

    ServiceFacilityDTO serviceFacilityToServiceFacilityDTO(ServiceFacility serviceFacility);

}
