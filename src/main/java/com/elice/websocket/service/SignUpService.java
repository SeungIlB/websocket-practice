package com.elice.websocket.service;

import com.elice.websocket.dto.SignUpRequestDTO;
import com.elice.websocket.entity.User;
import com.elice.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean signUpProc(SignUpRequestDTO request) {

        boolean isExisted = userRepository.existsByEmail(request.getEmail());
        if (isExisted) {
            return false;
        }

        User member = User.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role("USER")
                .build();

        userRepository.save(member);

        return userRepository.existsByEmail(member.getEmail());
    }
}
