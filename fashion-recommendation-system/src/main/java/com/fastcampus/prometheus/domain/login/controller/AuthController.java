package com.fastcampus.prometheus.domain.login.controller;


import com.fastcampus.prometheus.Session.SessionResponse;
import com.fastcampus.prometheus.config.AppConfig;
import com.fastcampus.prometheus.config.data.MemberSession;
import com.fastcampus.prometheus.domain.login.dto.LoginRequestDto;
import com.fastcampus.prometheus.domain.login.service.AuthService;
import com.fastcampus.prometheus.domain.member.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;


    @GetMapping("/foo")
    public Long foo(MemberSession memberSession) {
        log.info(">>>>{}", memberSession.id);
        return memberSession.id;
    }

    @GetMapping("/sign-in")
    public String showLogin(Model model) {
        model.addAttribute("loginDto", LoginRequestDto.builder().build());
        return "loginForm";
    }

    @PostMapping("/sign-in")
    public SessionResponse login(@RequestBody LoginRequestDto login) {
        Long memberId = authService.signIn(login);

        // 토큰 생성할때 사용
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        byte[] encodedKey = key.getEncoded();
//        String strKey = Base64.getEncoder().encodeToString(encodedKey); // String으로 변환

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + Duration.ofHours(1).toMillis()); // 1시간 후 만료

        String jws = Jwts.builder()
            .setSubject(String.valueOf(memberId))
            .signWith(key)
            .setIssuedAt(now) // 발급 시간
            .setExpiration(expiryDate) // 만료일
            .compact();

        return new SessionResponse(jws);

    }
}
