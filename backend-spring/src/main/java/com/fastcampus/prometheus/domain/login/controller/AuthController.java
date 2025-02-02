package com.fastcampus.prometheus.domain.login.controller;

import com.fastcampus.prometheus.Session.SessionResponse;
import com.fastcampus.prometheus.config.AppConfig;
import com.fastcampus.prometheus.config.data.MemberSession;
import com.fastcampus.prometheus.domain.login.dto.LoginRequestDto;
import com.fastcampus.prometheus.domain.login.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;


    @GetMapping("/foo")
    public Long foo(MemberSession memberSession) {
        log.info(">>>>{}", memberSession.id);
        return memberSession.id;
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        model.addAttribute("loginDto", LoginRequestDto.builder().build());
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginDto, Model model, HttpSession session) {
        System.out.println(loginDto.toString());
        /**
         * 로그인 실패 검증 만들기
         */
        Long memberId;
        try {
            memberId = authService.signIn(loginDto);
        } catch (IllegalArgumentException e) {
            // 로그인 실패 시
            // 뷰에 에러 메시지를 전달
            System.out.println(e);
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("loginDto", loginDto); // 이 부분 추가!
            return "member/loginForm"; // 다시 로그인 폼으로
        }
        System.out.println(memberId);

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

        SessionResponse sessionResponse = new SessionResponse(jws);
        System.out.println(sessionResponse.getAccessToken());

        session.setAttribute("jws", sessionResponse.getAccessToken());  // ✅ 세션 저장
        return "redirect:/";
    }
}