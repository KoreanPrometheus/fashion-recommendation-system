package com.fastcampus.prometheus.domain.member.service;


import com.fastcampus.prometheus.domain.member.controller.LoginController;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginService(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncdoer) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = passwordEncdoer;
    }



}
