package com.team5.hospital_here.board.dto.board;

import com.team5.hospital_here.board.domain.Board;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {
    private Long id;
    private String name;

//    public static BoardResponseDto fromEntity(Board board) {
//        return BoardResponseDto.builder()
//                .id(board.getId())
//                .name(board.getName())
//                .build();
//    }
}
