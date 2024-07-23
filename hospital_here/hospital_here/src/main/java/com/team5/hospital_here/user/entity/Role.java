package com.team5.hospital_here.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    DOCTOR("의사 회원"),
    USER("일반 회원"),
    ADMIN("관리자");

    private final String Role_status;
}
