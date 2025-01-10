package com.elice.websocket.controller;

import com.elice.websocket.dto.LoginRequestDTO;
import com.elice.websocket.dto.SignUpRequestDTO;
import com.elice.websocket.dto.UserSessionDTO;
import com.elice.websocket.service.LoginService;
import com.elice.websocket.service.SignUpService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final SignUpService signUpService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUpProc(@RequestBody SignUpRequestDTO request) {

        signUpService.signUpProc(request);

        return ResponseEntity.ok("Sign up successful");
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO request, HttpSession session) {
        UserSessionDTO userSession = loginService.login(request, session);
        session.setAttribute("user", userSession);  // 세션에 사용자 정보 저장
        return ResponseEntity.ok("Login successful for user: " + userSession.getEmail());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        loginService.logout(session);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserSessionDTO> getProfile(HttpSession session) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).build(); // 인증되지 않은 경우
        }
        return ResponseEntity.ok(user); // 세션에서 사용자 정보 반환
    }
}
