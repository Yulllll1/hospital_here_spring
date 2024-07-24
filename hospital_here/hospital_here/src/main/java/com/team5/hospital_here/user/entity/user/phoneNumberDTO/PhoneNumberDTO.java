package com.team5.hospital_here.user.entity.user.phoneNumberDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PhoneNumberDTO {
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "전화번호 형식이 옮바르지 않습니다.")
    @NotEmpty(message = "전화번호를 입력해 주세요.")
    private String phoneNumber;
}
