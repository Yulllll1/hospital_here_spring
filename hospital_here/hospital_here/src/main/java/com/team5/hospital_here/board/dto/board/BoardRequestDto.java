package com.team5.hospital_here.board.dto.board;

import com.team5.hospital_here.board.domain.Board;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private String name;

    public Board toEntity() {
        return Board.builder()
                .name(this.name)
                .build();
    }
}