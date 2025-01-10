package com.elice.websocket.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSessionDTO {
    private Long id;
    private String email;

    public UserSessionDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
