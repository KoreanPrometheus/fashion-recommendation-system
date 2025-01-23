package com.fastcampus.prometheus.domain.login.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class LoginRequestDto {

    private String email;

    private String password;


}
