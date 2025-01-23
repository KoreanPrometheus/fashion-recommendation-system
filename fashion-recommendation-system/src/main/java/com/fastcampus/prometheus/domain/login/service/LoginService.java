package com.fastcampus.prometheus.domain.login.service;


import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginEmail, String password){

        return memberRepository.findByEmail(loginEmail).orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

    }



}
