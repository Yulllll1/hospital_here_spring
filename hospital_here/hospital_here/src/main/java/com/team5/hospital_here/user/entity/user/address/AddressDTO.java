package com.team5.hospital_here.user.entity.user.address;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AddressDTO {
    @NotEmpty(message = "주소를 입력해 주세요.")
    private String address;
    @NotEmpty(message = "상세 주소를 입력해 주세요.")
    private String addressDetail;
}
