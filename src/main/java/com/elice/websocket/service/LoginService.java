package com.elice.websocket.service;

import com.elice.websocket.dto.LoginRequestDTO;
import com.elice.websocket.dto.UserSessionDTO;
import com.elice.websocket.entity.User;
import com.elice.websocket.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // SecurityContextHolder에 인증 정보 설정
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 세션에 SecurityContext 저장
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        // 사용자 정보를 DTO로 변환하여 반환
        UserSessionDTO userSession = new UserSessionDTO(user.getId(), user.getEmail());
        return userSession;
    }

    public void logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        SecurityContextHolder.clearContext(); // 인증 컨텍스트 초기화
    }
}
