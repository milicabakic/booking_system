package com.griddynamics.lidlbooking.business_logic.config;

import com.griddynamics.lidlbooking.business_logic.service.*;
import com.griddynamics.lidlbooking.commons.PasswordService;
import com.griddynamics.lidlbooking.commons.StudioClass;
import com.griddynamics.lidlbooking.domain.service.*;
import com.griddynamics.lidlbooking.persistance.manager.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
public class ServiceConfiguration {

    private final JWTService jwtService;


    @Autowired
    public ServiceConfiguration(final JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public AmenityService amenityService(final AmenityManager amenityManager) {
        return new AmenityServiceImpl(amenityManager);
    }

    @Bean
    public CityService cityService(final CityManager cityManager) {
        return new CityServiceImpl(cityManager);
    }

    @Bean
    public CountryService countryService(final CountryManager countryManager) {
        return new CountryServiceImpl(countryManager);
    }

    @Bean
    public ServiceFacilityService serviceFacilityService(final ServiceFacilityManager manager,
                                                         final BookingProviderService bookingProviderService,
                                                         final CityService cityService,
                                                         final CountryService countryService) {
        return new ServiceFacilityServiceImpl(manager, bookingProviderService, cityService, countryService);
    }

    @Bean
    public StudioService studioService(final StudioManager studioServiceMapper,
                                       final ServiceFacilityService serviceFacilityService,
                                       final StudioClassifierService classifierService,
                                       final AmenityService amenityService,
                                       final JWTService jwtService,
                                       final BookingProviderService bookingProviderService) {
        return new StudioServiceImpl(studioServiceMapper, serviceFacilityService,
                classifierService, amenityService, jwtService, bookingProviderService);
    }


    @Bean
    public BookingAdminService bookingAdminService(final BookingAdminManager bookingAdminManager,
                                                   final PasswordService passwordService) {
        return new BookingAdminServiceImpl(bookingAdminManager, passwordService, jwtService);
    }

    @Bean
    public BookingProviderService bookingProviderService(final BookingProviderManager bookingProviderManager,
                                                         final PasswordService passwordService) {
        return new BookingProviderServiceImpl(bookingProviderManager, passwordService, jwtService);
    }

    @Bean
    public BookingUserService bookingUserService(final BookingUserManager bookingUserManager,
                                                 final PasswordService passwordService) {
        return new BookingUserServiceImpl(bookingUserManager, passwordService, jwtService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SeasonService seasonService(final SeasonManager seasonManager,
                                       final JWTService jwtService,
                                       final BookingProviderService providerService,
                                       final StudioService studioService) {
        return new SeasonServiceImpl(seasonManager, jwtService, providerService, studioService);
    }

    @Bean
    public BookingService bookingService(final BookingManager bookingManager, final StudioService studioService,
                                         final BookingUserService bookingUserService, final SeasonService seasonService) {
        return new BookingServiceImpl(bookingManager, studioService, jwtService, bookingUserService, seasonService);
    }

    @Bean
    public StudioClassifierService studioClassifierService(final @Value("#{${booking.studio.classification}}")
                                                               Map<StudioClass, List<String>> studioClasses) {
        return new StudioClassifierService(studioClasses);
    }

}
