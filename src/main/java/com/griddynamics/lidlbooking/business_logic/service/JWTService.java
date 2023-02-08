package com.griddynamics.lidlbooking.business_logic.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.griddynamics.lidlbooking.commons.JWTUtils;
import com.griddynamics.lidlbooking.commons.SecurityConstants;
import com.griddynamics.lidlbooking.commons.errors.AuthenticationException;
import com.griddynamics.lidlbooking.commons.errors.ErrorMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTService {

    public static final String EMPTY_STRING = "";


    public JWTService() {

    }

    public String generateToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new AuthenticationException(ErrorMessage.TOKEN_EXPIRED);
        }
        return getJWT(auth);
    }

    public Map<String, Object> successfulAuthorization(final String authorizationHeader) {
        Map<String, Object> tokenInfo = new HashMap<>();
        DecodedJWT jwt = decodeJWT(authorizationHeader);
        String username = jwt.getSubject();

        if (username != null) {
            String[] roles = jwt.getClaim(SecurityConstants.TOKEN_CLAIMS_KEY).asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = getAuthorities(roles);
            fillMap(tokenInfo, username, authorities);
        }

        return tokenInfo;
    }

    public String whoAmI() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private DecodedJWT decodeJWT(final String authorizationHeader) {
        return JWT.require(generateSignature())
                .build()
                .verify(extractToken(authorizationHeader));
    }

    private String extractToken(final String authorizationHeader) {
        return authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, EMPTY_STRING);
    }

    private void fillMap(final Map<String, Object> map, final String username,
                         final Collection<SimpleGrantedAuthority> authorities) {
        map.put(JWTUtils.USERNAME, username);
        map.put(JWTUtils.AUTHORITIES, authorities);
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(final String[] roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Arrays.stream(roles)
                .forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
        return authorities;
    }

    private String getJWT(final Authentication auth) {
        String token = generateToken(auth);
        String prefix = SecurityConstants.TOKEN_PREFIX;
        return prefix.concat(token);
    }

    private String generateToken(final Authentication auth) {
        User user = (User) auth.getPrincipal();

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationTime())
                .withArrayClaim(SecurityConstants.TOKEN_CLAIMS_KEY, getUserRoles(user))
                .sign(generateSignature());

        return accessToken;
    }

    private String[] getUserRoles(final User user) {
        List<String> authorities = getAuthorities(user);
        return authorities.toArray(new String[authorities.size()]);
    }

    private List<String> getAuthorities(final User user) {
        return user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    private Algorithm generateSignature() {
        return Algorithm.HMAC512(SecurityConstants.SECRET.getBytes());
    }

    private Date generateExpirationTime() {
        return new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION);
    }

}
