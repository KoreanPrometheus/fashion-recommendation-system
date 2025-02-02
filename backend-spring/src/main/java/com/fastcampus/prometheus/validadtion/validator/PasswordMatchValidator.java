package com.fastcampus.prometheus.validadtion.validator;

import com.fastcampus.prometheus.domain.member.dto.MemberDto;
import com.fastcampus.prometheus.domain.member.entity.Member;
import com.fastcampus.prometheus.domain.member.service.MemberService;
import com.fastcampus.prometheus.validadtion.annotation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, MemberDto> {

    private final MemberService memberService;

    public boolean isValid(MemberDto memberDto, ConstraintValidatorContext context) {
        if (memberDto.getPassword() == null || memberDto.getPasswordConfirm() == null) {
            return false;
        }

        // ✅ 이메일 중복 검사 (유효성 검사 방식으로 처리)
        Optional<Member> member = memberService.getId(memberDto.getEmail());
        if (member.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이미 가입된 이메일입니다.")
                    .addPropertyNode("email") // ❗ "email" 필드에 에러 표시
                    .addConstraintViolation();
            return false;
        }

        // ✅ 비밀번호 일치 검사
        if (!memberDto.getPassword().equals(memberDto.getPasswordConfirm())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("새 비밀번호가 일치하지 않습니다.")
                    .addPropertyNode("passwordConfirm") // ❗ "passwordConfirm" 필드에 에러 표시
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
