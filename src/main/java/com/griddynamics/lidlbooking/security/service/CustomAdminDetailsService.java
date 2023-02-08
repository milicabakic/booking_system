package com.griddynamics.lidlbooking.security.service;

import com.griddynamics.lidlbooking.commons.SecurityConstants;
import com.griddynamics.lidlbooking.domain.model.Role;
import com.griddynamics.lidlbooking.persistance.entity.BookingAdminEntity;
import com.griddynamics.lidlbooking.persistance.repository.BookingAdminRepository;
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
public class CustomAdminDetailsService implements UserDetailsService {

    private final BookingAdminRepository repository;

    @Autowired
    public CustomAdminDetailsService(final BookingAdminRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<BookingAdminEntity> admin = repository.findByUsername(username);

        if (admin.isEmpty()) {
            throw new UsernameNotFoundException(SecurityConstants.AUTHENTICATION_FAILED);
        }

        return new User(admin.get().getUsername(), admin.get().getPassword(), getAuthorities());
    }

    private List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
        return authorities;
    }
}
