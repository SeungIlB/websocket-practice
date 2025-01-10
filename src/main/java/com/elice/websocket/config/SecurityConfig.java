package com.elice.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF 비활성화 (필요 시 활성화 고려)
                .csrf(csrf -> csrf.disable())

                // 권한 및 접근 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/mainPage").permitAll() // 메인 페이지 누구나 접근 가능
                        .requestMatchers("/signup").permitAll()  // 회원가입 누구나 접근 가능
                        .requestMatchers("/login").permitAll()   // 로그인 페이지 누구나 접근 가능
                        .requestMatchers("/profile").permitAll()
                        .requestMatchers("/adminPage").hasRole("ADMIN") // 관리자 페이지는 ADMIN만 접근 가능
                        .requestMatchers("/myPage/**").hasAnyRole("ADMIN", "USER") // 사용자 페이지는 ADMIN, USER 모두 접근 가능
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )

                // 세션 관리 설정
                .sessionManagement(session -> session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession) // 세션 고정 보호
                        .maximumSessions(1)  // 최대 세션 수 1개
                        .maxSessionsPreventsLogin(true) // 세션 초과 시 추가 로그인을 방지
                )

                // 폼 로그인 설정
                .formLogin(AbstractHttpConfigurer::disable)
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                );

        return httpSecurity.build();
    }

    // 비밀번호 암호화를 위한 Bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
