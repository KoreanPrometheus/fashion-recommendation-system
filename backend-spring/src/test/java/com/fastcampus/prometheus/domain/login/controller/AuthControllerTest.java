package com.fastcampus.prometheus.domain.login.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fastcampus.prometheus.domain.login.dto.LoginRequestDto;
import com.fastcampus.prometheus.domain.login.service.AuthService;
import com.fastcampus.prometheus.domain.member.MemberGender;
import com.fastcampus.prometheus.domain.member.MemberRole;
import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.repository.MemberRepository;
import com.fastcampus.prometheus.domain.member.repository.SessionRepository;
import com.fastcampus.prometheus.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 세션 테스트")
    void testLoginSession() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
            .name("신동준")
            .email("tlsehdwns147@naver.com")
            .password("1234")
            .build());

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
            .email("tlsehdwns147@naver.com")
            .password("1234")
            .build();

        String json = objectMapper.writeValueAsString(loginRequestDto);

        //expected
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @Transactional // 공부더해야할듯.
    @DisplayName("로그인 성공 후 세션 테스트")
    void test2() throws Exception {
        // given

        MemberDto memberDto = MemberDto.builder() // 클라이언트가 회원가입할 때 입력하는 정보
            .email("tlsehdwns147@naver.com")
            .name("신동준")
            .memberGender(MemberGender.MALE)
            .password("1234")
            .memberRole(MemberRole.USER)
            .build();

        Member member = memberService.join(memberDto); // 이제 회원가입하고. 비밀번호 암호화 됐고

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
            .email("tlsehdwns147@naver.com")
            .password("1234")
            .build();

//        authService.signIn(loginRequestDto); // 로그인하고
        String json = objectMapper.writeValueAsString(loginRequestDto);


        //expected
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andDo(print());

//        Member loggedInMember = memberRepository.findById(member.getId()).orElseThrow(RuntimeException::new);

        assertEquals(1L, member.getSessions().size());
        Assertions.assertEquals(1L, sessionRepository.count());
    }

}