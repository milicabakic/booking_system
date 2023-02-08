package com.griddynamics.lidlbooking.api.error_handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.lidlbooking.api.dto.ErrorDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static com.griddynamics.lidlbooking.api.error_handler.ExceptionHandlerAdvice.getTime;

@Component
public class ErrorHandlingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException e) {

            ErrorDTO errorDTO = new ErrorDTO(UUID.randomUUID(), e.getMessage(), getTime());
            logger.error(errorDTO + " : " + Arrays.toString(e.getStackTrace()));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(convertObjectToJson(errorDTO));
        }
    }

    private String convertObjectToJson(final Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
