package com.griddynamics.lidlbooking.security.config;

import com.griddynamics.lidlbooking.security.filters.LoginFilter;
import com.griddynamics.lidlbooking.security.service.CustomAdminDetailsService;
import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@Order(AdminConfigAdapter.FOUR)
public class AdminConfigAdapter extends WebSecurityConfigurerAdapter {

    public static final int FOUR = 4;
    private final CustomAdminDetailsService detailsService;
    private final JWTService jwtService;
    public static final String LOGIN_PATH = "/auth/admin/login";
    public static final String AUTH_PROVIDER_PATH = "/auth/admin/**";

    @Autowired
    public AdminConfigAdapter(final CustomAdminDetailsService detailsService, final JWTService jwtService) {
        this.detailsService = detailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(), jwtService);
        loginFilter.setFilterProcessesUrl(LOGIN_PATH);

        http.antMatcher(AUTH_PROVIDER_PATH)
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_PROVIDER_PATH)
                .permitAll()
                .and()
                .addFilter(loginFilter)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService);
    }
}
