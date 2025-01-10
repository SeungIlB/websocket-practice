package com.elice.websocket.service;

import com.elice.websocket.dto.LoginRequestDTO;
import com.elice.websocket.dto.UserSessionDTO;
import com.elice.websocket.entity.User;
import com.elice.websocket.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserSessionDTO login(LoginRequestDTO request, HttpSession session) {
        // 이메일로 사용자 검색
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 패스워드 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // 세션에 사용자 정보 저장
        UserSessionDTO userSession = new UserSessionDTO(user.getId(), user.getEmail());
        session.setAttribute("user", userSession);  // 세션에 사용자 정보 저장

        return userSession;  // 로그인 성공시 사용자 정보 반환
    }

    public void logout(HttpSession session) {
        session.invalidate();  // 세션 무효화
    }
}
