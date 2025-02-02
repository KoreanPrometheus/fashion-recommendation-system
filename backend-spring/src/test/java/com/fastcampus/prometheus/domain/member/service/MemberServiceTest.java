package com.fastcampus.prometheus.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.prometheus.domain.login.service.LoginService;
import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clean(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1(){
        //given
        MemberDto memberDto = MemberDto.builder()
            .email("tlsehdwns147@naver.com")
            .password("1234")
            .name("신동준")
            .build();

        //when
        Member member1 = memberService.join(memberDto);

        //then
        assertEquals(1, memberRepository.count());

        System.out.println(member1.getPassword());

        Member member = memberRepository.findAll().iterator().next();
        assertEquals("tlsehdwns147@naver.com", member.getEmail());
        assertEquals("신동준", member.getName());
        assertNotNull(member.getPassword());
        assertNotEquals("1234", member.getPassword());
    }

    @Test
    @DisplayName("비밀번호 암호화 성공")
    void test2() {
        // given
        MemberDto memberDto = MemberDto.builder()
            .email("tlsehdwns147@naver.com")
            .password("1234")
            .name("신동준")
            .build();

        // when
        memberService.join(memberDto);

        // then
        Member member = memberRepository.findAll().iterator().next();
        assertTrue(passwordEncoder.matches("1234", member.getPassword()));
    }







}