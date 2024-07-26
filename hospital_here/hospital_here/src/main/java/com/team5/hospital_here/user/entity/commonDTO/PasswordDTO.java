package com.team5.hospital_here.user.entity.commonDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PasswordDTO {

    @NotEmpty(message = "기존 비밀번호를 입력해 주세요.")
    public String verifyPassword;
    @NotEmpty(message = "변경할 비밀번호를 입력해 주세요.")
    public String alterPassword;
}
