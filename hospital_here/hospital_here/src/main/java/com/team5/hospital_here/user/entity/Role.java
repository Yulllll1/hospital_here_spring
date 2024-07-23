package com.team5.hospital_here.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    DOCTOR("ROLE_DOCTOR"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String Role_status;
}
