package com.team5.hospital_here.user.entity.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;


@Getter
public class LoginDTO {

    @NotEmpty(message = "이메일을 입력해 주세요.")
    @Email(message = "옮바른 이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;
}
