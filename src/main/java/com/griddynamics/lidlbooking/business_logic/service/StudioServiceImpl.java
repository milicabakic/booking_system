package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.domain.model.Amenity;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.domain.model.Studio;
import com.griddynamics.lidlbooking.domain.model.structure.ServiceFacilityStructure;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import com.griddynamics.lidlbooking.domain.service.AmenityService;
import com.griddynamics.lidlbooking.domain.service.ServiceFacilityService;
import com.griddynamics.lidlbooking.domain.service.StudioService;
import com.griddynamics.lidlbooking.persistance.manager.StudioManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.STUDIO_NULL;

public class StudioServiceImpl implements StudioService {


    private final StudioManager manager;
    private final ServiceFacilityService serviceFacilityService;

    private final StudioClassifierService studioClassifier;

    private final AmenityService amenityService;
    private JWTService jwtService;
    private final BookingProviderService bookingProviderService;


    public StudioServiceImpl(final StudioManager studioManager,
                             final ServiceFacilityService serviceFacilityService,
                             final StudioClassifierService studioClassifier,
                             final AmenityService amenityService,
                             final JWTService jwtService,
                             final BookingProviderService bookingProviderService) {
        this.manager = studioManager;
        this.serviceFacilityService = serviceFacilityService;
        this.jwtService = jwtService;
        this.bookingProviderService = bookingProviderService;
        this.studioClassifier = studioClassifier;
        this.amenityService = amenityService;
    }

    @Override
    public Studio findById(final Long id) {
        return manager.findById(id);
    }

    @Override
    public List<Studio> findAll() {
        return manager.findAll();
    }

    @Override
    public Studio createStudio(final Studio studio) {
        if (studio == null) {
            throw new BadRequestException("Studio must not be null");
        }
        BookingProvider studioCreator = findStudioCreator();
        studio.setBookingProvider(studioCreator);
        return saveStudio(studio);
    }

    @Override
    public void deleteStudio(final Long id) {
        manager.deleteById(id);
    }

    @Override
    public void deleteAllStudios() {
        manager.deleteAll();
    }

    @Override
    public Studio updateStudio(final Studio studio, final Long id) {
        if (studio == null) {
            throw new BadRequestException(STUDIO_NULL);
        }
        Studio entity = manager.findById(id);
        studio.setId(entity.getId());
        return saveStudio(studio);
    }

    @Override
    public List<Studio> findAllForBooking() {
        return manager.findAllForBooking();
    }

    @Override
    public Studio updateStudioAmenities(final Long id, final List<String> amenities) {
        if (amenities == null) {
            throw new BadRequestException(STUDIO_NULL);
        }

        Studio studio = findStudioById(id);
        studio.setClassificationType(getClassificationType(amenities));

        Set<Amenity> amenitySet = new HashSet<>(getAmenityList(amenities));
        studio.setAmenities(amenitySet);
        return manager.save(studio);
    }

    private Studio findStudioById(final Long id) {
        return manager.findById(id);
    }

    private Studio saveStudio(final Studio studio) {
        ServiceFacility serviceFacility = serviceFacilityService.getFacilityById(studio
                .getServiceFacility().getId());

        ServiceFacilityStructure facility = new ServiceFacilityStructure(serviceFacility.getId(),
                serviceFacility.getAddress(), serviceFacility.getPostalCode(), serviceFacility.getNumberOfFloors(),
                serviceFacility.getName(), serviceFacility.getContactPhone(), serviceFacility.getDescription(),
                serviceFacility.getCheckinTime(), serviceFacility.getCheckoutTime());

        studio.setServiceFacility(facility);
        return manager.save(studio);
    }

    private BookingProvider findStudioCreator() {
        String username = jwtService.whoAmI();
        return bookingProviderService.findBookingProviderByUsername(username);
    }


    private String getClassificationType(final List<String> studioAmenities) {
        return studioClassifier.classifyStudio(studioAmenities);
    }

    private List<Amenity> getAmenityList(final List<String> amenityNames) {
        return amenityService.findAmenitiesByName(amenityNames);
    }

}
