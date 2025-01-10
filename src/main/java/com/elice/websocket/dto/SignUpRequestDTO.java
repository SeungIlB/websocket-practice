package com.elice.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequestDTO {
    private String name;
    private String email;
    private String password;
}