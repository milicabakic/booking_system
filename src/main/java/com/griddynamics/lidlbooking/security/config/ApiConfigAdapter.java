package com.griddynamics.lidlbooking.security.config;

import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import com.griddynamics.lidlbooking.api.error_handler.ErrorHandlingFilter;
import com.griddynamics.lidlbooking.security.filters.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiConfigAdapter extends WebSecurityConfigurerAdapter {

    private final JWTService jwtService;
    public static final String USER_PATH = "/api/users/**";
    public static final String PROVIDER_PATH = "/api/provider/**";
    public static final String ADMIN_PATH = "/api/admin/**";
    public static final String AMENITY_PATH = "/api/amenity/**";
    public static final String FACILITY_PATH = "/api/facility/**";
    public static final String STUDIO_PATH = "/api/studio/**";
    public static final String SEASON_PATH = "/api/seasons/**";
    public static final String BOOKING_PATH = "/api/booking/**";

    @Autowired
    public ApiConfigAdapter(final JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers(BOOKING_PATH).access("hasAnyAuthority('USER','PROVIDER','ADMIN')")
                .antMatchers(FACILITY_PATH, STUDIO_PATH, SEASON_PATH).access("hasAnyAuthority('PROVIDER','ADMIN')")
                .antMatchers(ADMIN_PATH, AMENITY_PATH).access("hasAuthority('ADMIN')")
                .antMatchers(PROVIDER_PATH).access("hasAuthority('PROVIDER')")
                .antMatchers(USER_PATH).access("hasAuthority('USER')")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new ErrorHandlingFilter(), JWTAuthenticationFilter.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManagerBean(), jwtService))
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
