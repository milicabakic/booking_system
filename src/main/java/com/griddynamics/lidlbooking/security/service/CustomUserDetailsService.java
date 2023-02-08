package com.griddynamics.lidlbooking.security.service;

import com.griddynamics.lidlbooking.commons.SecurityConstants;
import com.griddynamics.lidlbooking.domain.model.Role;
import com.griddynamics.lidlbooking.persistance.entity.BookingUserEntity;
import com.griddynamics.lidlbooking.persistance.repository.BookingUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BookingUserRepository repository;


    @Autowired
    public CustomUserDetailsService(final BookingUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<BookingUserEntity> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            throw new RuntimeException(SecurityConstants.AUTHENTICATION_FAILED);
        }
        return new User(username, user.get().getPassword(), getAuthorities());
    }

    private List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.USER.toString()));
        return authorities;
    }
}
