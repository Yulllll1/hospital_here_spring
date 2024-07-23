package com.team5.hospital_here.user.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;


@Getter
public class LoginDTO {

    @NotEmpty(message = "이메일을 입력해 주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;
}
