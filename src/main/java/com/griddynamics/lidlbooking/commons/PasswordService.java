package com.griddynamics.lidlbooking.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordService(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public boolean matches(final String typedPassword, final String password) {
        return passwordEncoder.matches(typedPassword, password);
    }

    public String encode(final String password) {
        return passwordEncoder.encode(password);
    }

}
