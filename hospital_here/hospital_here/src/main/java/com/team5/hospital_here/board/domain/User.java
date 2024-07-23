package com.team5.hospital_here.board.domain;

import com.team5.hospital_here.board.dto.user.UserResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String pw;

    public UserResponseDto toResponseDto() {
        return UserResponseDto.builder()
                .id(this.id)
                .email(this.email)
                .pw(this.pw)
                .build();
    }
}