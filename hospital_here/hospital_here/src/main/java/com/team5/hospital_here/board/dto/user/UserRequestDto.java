package com.team5.hospital_here.board.dto.user;

import com.team5.hospital_here.board.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String email;
    private String pw;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .pw(this.pw)
                .build();
    }
}