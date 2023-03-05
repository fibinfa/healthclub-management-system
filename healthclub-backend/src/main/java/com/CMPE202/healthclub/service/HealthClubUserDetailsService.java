package com.CMPE202.healthclub.service;


import com.CMPE202.healthclub.model.HealthClubUserDetails;
import com.CMPE202.healthclub.model.User;
import com.CMPE202.healthclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class HealthClubUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        user.orElseThrow(()->new UsernameNotFoundException("Not found: "+ username));
        return user.map(HealthClubUserDetails::new).get();
    }
}
