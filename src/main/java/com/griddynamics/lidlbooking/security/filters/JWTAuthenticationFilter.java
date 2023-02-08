package com.griddynamics.lidlbooking.security.filters;

import com.griddynamics.lidlbooking.business_logic.service.JWTService;
import com.griddynamics.lidlbooking.commons.JWTUtils;
import com.griddynamics.lidlbooking.commons.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final JWTService jwtService;

    @Autowired
    public JWTAuthenticationFilter(final AuthenticationManager authenticationManager, final JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(SecurityConstants.TOKEN_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken token = getAuthentication(request, authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request,
                                                                  final String authorizationHeader) {
        if (authorizationHeader != null) {
            Map<String, Object> tokenInfo = jwtService.successfulAuthorization(authorizationHeader);
            if (!tokenInfo.isEmpty()) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(tokenInfo.get(JWTUtils.USERNAME),
                                null,
                                (Collection<? extends GrantedAuthority>) tokenInfo.get(JWTUtils.AUTHORITIES));
                return usernamePasswordAuthenticationToken;
            }
        }

        return null;
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        return request.getRequestURL().toString().endsWith("/login");
    }
}
