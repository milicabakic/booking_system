package com.griddynamics.lidlbooking.persistance.mapper;

import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.persistance.entity.ServiceFacilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceFacilityManagerMapper {


    ServiceFacility serviceFacilityEntityToServiceFacility(ServiceFacilityEntity serviceFacilityEntity);

    List<ServiceFacility> serviceFacilityEntityListToServiceFacilityList(
            List<ServiceFacilityEntity> serviceFacilityEntities);

    ServiceFacilityEntity serviceFacilityToServiceFacilityEntity(ServiceFacility facility);
}
