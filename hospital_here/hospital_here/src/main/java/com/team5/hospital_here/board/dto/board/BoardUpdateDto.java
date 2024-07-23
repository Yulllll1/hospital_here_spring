package com.team5.hospital_here.board.dto.board;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateDto {
    private Long id;
    private String name;
}
