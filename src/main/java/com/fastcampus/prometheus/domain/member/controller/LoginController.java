package com.fastcampus.prometheus.domain.member.controller;


import com.fastcampus.prometheus.domain.member.dto.request.LoginRequest;
import com.fastcampus.prometheus.domain.member.dto.response.SessionResponse;
import com.fastcampus.prometheus.domain.member.service.LoginService;
import io.jsonwebtoken.Jwts;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody LoginRequest loginRequest) {

        SecretKey key = Jwts.SIG.HS256.key().build(); //키 값을 매번 로그인할때마다 바뀌어버리면..? 변하면 안되지 않나.
        String jws = Jwts.builder().subject("Joe").signWith(key).compact();

        return new SessionResponse(jws);
    }
}
