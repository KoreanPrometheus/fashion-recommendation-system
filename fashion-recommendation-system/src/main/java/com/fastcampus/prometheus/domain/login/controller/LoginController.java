package com.fastcampus.prometheus.domain.login.controller;


import com.fastcampus.prometheus.domain.login.dto.LoginRequestDto;
import com.fastcampus.prometheus.domain.login.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String showLogin(){
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto")LoginRequestDto loginRequestDto){

        loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return "redirect:/";
    }
}
