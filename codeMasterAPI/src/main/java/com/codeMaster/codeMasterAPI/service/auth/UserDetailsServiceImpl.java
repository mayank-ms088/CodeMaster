package com.codeMaster.codeMasterAPI.service.auth;

import com.codeMaster.codeMasterAPI.models.User;
import com.codeMaster.codeMasterAPI.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Authenticating user with email " + username);
        Optional<User> userOptional = userRepository.findByEmail(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user found with email " + username));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),true, true, true, true, getAuthorities("USER"));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    public Optional<User> getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return userRepository.findByEmail(principal.getUsername());
    }
}
