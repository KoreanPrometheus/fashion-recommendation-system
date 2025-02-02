package com.fastcampus.prometheus.domain.member.dto;

import com.fastcampus.prometheus.domain.member.MemberGender;
import com.fastcampus.prometheus.domain.member.MemberRole;
import com.fastcampus.prometheus.validadtion.annotation.PasswordMatch;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@PasswordMatch
public class MemberDto {

    private String email;

    private String name;

    private MemberGender memberGender;

    private String password;

    private String passwordConfirm;

    private MemberRole memberRole;

    @Override
    public String toString() {
        return "MemberDto{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", memberGender=" + memberGender +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", memberRole=" + memberRole +
                '}';
    }
}
