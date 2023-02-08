package com.griddynamics.lidlbooking.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.lidlbooking.api.dto.users.LoginFormDTO;
import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import com.griddynamics.lidlbooking.commons.SecurityConstants;
import com.griddynamics.lidlbooking.commons.errors.BadRequestException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public LoginFilter(final AuthenticationManager authenticationManager, final JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginFormDTO user = new ObjectMapper().readValue(request.getInputStream(), LoginFormDTO.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(), Collections.emptyList());

            return authenticationManager.authenticate(token);
        } catch (Exception e) {
            throw new BadRequestException(ErrorMessage.INVALID_CREDENTIALS);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest req, final HttpServletResponse res,
                                            final FilterChain chain, final Authentication auth)
            throws IOException, ServletException {
        setAuthentication(auth);
        Map<String, String> tokens = getTokens();
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(res.getOutputStream(), tokens);
    }

    private void setAuthentication(final Authentication auth) {
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Map<String, String> getTokens() {
        String key = SecurityConstants.ACCESS_TOKEN;
        String jwt = jwtService.generateToken();
        return Collections.singletonMap(key, jwt);
    }
}
