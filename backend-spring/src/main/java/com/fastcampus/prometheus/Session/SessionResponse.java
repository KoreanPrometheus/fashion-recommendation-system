package com.fastcampus.prometheus.Session;

import com.fastcampus.prometheus.domain.login.service.AuthService;
import lombok.Getter;

@Getter
public class SessionResponse {

    private final String accessToken;

    public SessionResponse(String accessToken){
        this.accessToken = accessToken;
    }


}
