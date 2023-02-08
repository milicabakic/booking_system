package com.griddynamics.lidlbooking.business_logic.service;

import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.domain.model.BookingProvider;
import com.griddynamics.lidlbooking.domain.model.City;
import com.griddynamics.lidlbooking.domain.model.Country;
import com.griddynamics.lidlbooking.domain.model.ServiceFacility;
import com.griddynamics.lidlbooking.domain.model.structure.BookingProviderStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CityStructure;
import com.griddynamics.lidlbooking.domain.model.structure.CountryStructure;
import com.griddynamics.lidlbooking.domain.service.BookingProviderService;
import com.griddynamics.lidlbooking.domain.service.CityService;
import com.griddynamics.lidlbooking.domain.service.CountryService;
import com.griddynamics.lidlbooking.domain.service.ServiceFacilityService;
import com.griddynamics.lidlbooking.persistance.manager.ServiceFacilityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.CITY_NOT_IN_COUNTRY;
import static com.griddynamics.lidlbooking.commons.errors.ErrorMessage.SERVICE_FACILITY_NULL;

public class ServiceFacilityServiceImpl implements ServiceFacilityService {
    private final BookingProviderService bookingProviderService;

    private final CityService cityService;

    private final CountryService countryService;

    private final ServiceFacilityManager manager;

    @Autowired
    public ServiceFacilityServiceImpl(final ServiceFacilityManager manager,
                                      final BookingProviderService bookingProviderService,
                                      final CityService cityService, final CountryService countryService) {
        this.manager = manager;
        this.bookingProviderService = bookingProviderService;
        this.cityService = cityService;
        this.countryService = countryService;

    }

    @Override
    public ServiceFacility getFacilityById(final Long id) {
        return manager.findById(id);
    }

    @Override
    public List<ServiceFacility> getFacilities() {
        return manager.findAll();
    }

    @Override
    public void deleteFacility(final Long id) {
        manager.deleteById(id);
    }


    @Override
    public void deleteAllFacility() {
        manager.deleteAll();
    }

    @Override
    public ServiceFacility createFacility(final ServiceFacility facility) {
        if (facility == null) {
            throw new BadRequestException(SERVICE_FACILITY_NULL);
        }
        return saveServiceFacility(facility);
    }

    @Override
    public ServiceFacility updateFacility(final ServiceFacility facility, final Long id) {
        if (facility == null) {
            throw new BadRequestException(SERVICE_FACILITY_NULL);
        }
        manager.findById(id);
        facility.setId(id);
        return saveServiceFacility(facility);
    }

    private ServiceFacility saveServiceFacility(final ServiceFacility facility) {
        BookingProvider bookingProvider = bookingProviderService.findBookingProviderById(
                facility.getBookingProvider().getId());
        City city = cityService.getCityById(facility.getCity().getId());
        Country country = countryService.getCountryById(facility.getCountry().getIso3());
        if (!country.getIso3().equals(city.getCountry().getIso3())) {
            throw new BadRequestException(CITY_NOT_IN_COUNTRY);
        }

        CountryStructure countryStructure = new CountryStructure(country.getIso3(), country.getName());
        BookingProviderStructure bookingProviderStructure = new BookingProviderStructure(bookingProvider.getId(),
                bookingProvider.getFirstName(), bookingProvider.getLastName(), bookingProvider.getJmbg(),
                bookingProvider.getEmail(), bookingProvider.getPhoneNumber());

        facility.setBookingProvider(bookingProviderStructure);
        facility.setCountry(countryStructure);
        facility.setCity(new CityStructure(city.getId(), city.getName(), city.getProvince(), countryStructure));


        return manager.save(facility);
    }
}

