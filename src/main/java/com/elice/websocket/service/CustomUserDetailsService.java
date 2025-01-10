package com.elice.websocket.service;

import com.elice.websocket.CustomUserDetails;
import com.elice.websocket.entity.User;
import com.elice.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(Email);
        if (userOptional.isPresent()) {

            return new CustomUserDetails(userOptional.get());
        }

        return null;
    }
}
