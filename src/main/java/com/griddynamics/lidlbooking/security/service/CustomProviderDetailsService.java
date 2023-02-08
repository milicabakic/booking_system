package com.griddynamics.lidlbooking.security.service;

import com.griddynamics.lidlbooking.commons.SecurityConstants;
import com.griddynamics.lidlbooking.domain.model.Role;
import com.griddynamics.lidlbooking.persistance.entity.BookingProviderEntity;
import com.griddynamics.lidlbooking.persistance.repository.BookingProviderRepository;
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
public class CustomProviderDetailsService implements UserDetailsService {

    private final BookingProviderRepository repository;


    @Autowired
    public CustomProviderDetailsService(final BookingProviderRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<BookingProviderEntity> provider = repository.findByUsername(username);

        if (provider.isEmpty()) {
            throw new UsernameNotFoundException(SecurityConstants.AUTHENTICATION_FAILED);
        }

        return new User(provider.get().getUsername(), provider.get().getPassword(), getAuthorities());
    }

    private List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.PROVIDER.toString()));
        return authorities;
    }
}
