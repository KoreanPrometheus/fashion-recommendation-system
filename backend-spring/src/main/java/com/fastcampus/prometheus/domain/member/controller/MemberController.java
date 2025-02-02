package com.fastcampus.prometheus.domain.member.controller;


import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String createForm() {
        return "member/registerForm";
    }

    @PostMapping
    public String create(@Valid MemberDto memberDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 1) BindingResult에서 모든 에러(ObjectError)를 가져온다
            List<ObjectError> allErrors = result.getAllErrors();

            // 2) 각 ObjectError에서 에러 메시지(defaultMessage)만 추출하여 List<String>으로 변환
            List<String> errorMessages = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());

            // 3) 모델에 담는다
            model.addAttribute("errorMessage", errorMessages);
            return "member/registerForm";
        }
        System.out.println(memberDto.toString());
        memberService.join(memberDto);

        return "redirect:/login";

    }
}
