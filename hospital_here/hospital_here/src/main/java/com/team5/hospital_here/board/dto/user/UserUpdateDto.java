package com.team5.hospital_here.board.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    private Long id;
    private String email;
    private String pw;
}
