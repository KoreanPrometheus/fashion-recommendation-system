package com.fastcampus.prometheus.domain.login.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fastcampus.prometheus.domain.login.dto.LoginRequestDto;
import com.fastcampus.prometheus.domain.member.MemberType;
import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import com.fastcampus.prometheus.domain.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class AuthServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceTest.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 - 비밀번호 암호화 확인")
    void test1() {
        // given
        MemberDto memberDto = MemberDto.builder()
            .email("tlsehdwns147@naver.com")
            .name("신동준")
            .password("1234")
            .memberType(MemberType.USER)
            .build();

        log.info("회원가입 요청: {}", memberDto);

        // when
        Member savedMember = memberService.join(memberDto);

        // 로그 추가
        log.info("회원 저장 완료: {}", savedMember);
        log.info("원본 비밀번호: {}", memberDto.getPassword());
        log.info("암호화된 비밀번호: {}", savedMember.getPassword());

        //then
        assertThat(savedMember.getEmail()).isEqualTo(memberDto.getEmail());
        assertThat(
            passwordEncoder.matches(memberDto.getPassword(), savedMember.getPassword())).isTrue();
    }

    @Test
    @DisplayName("로그인 성공")
    void test2() {

    }
}