package com.team5.hospital_here.bookMark.controller;


import com.team5.hospital_here.bookMark.entity.BookMarkDTO;
import com.team5.hospital_here.bookMark.service.BookMarkService;
import com.team5.hospital_here.common.jwt.CustomUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookmark")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    public BookMarkController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }

    @PostMapping
    public ResponseEntity<String> addBookMark(@AuthenticationPrincipal CustomUser customUser, @RequestBody BookMarkDTO bookMarkDTO) {

        bookMarkService.addBookMark(bookMarkDTO);
        return ResponseEntity.ok("북마크 추가 성공");
    }

    @DeleteMapping("/{hospitalId}")
    public ResponseEntity<String> deleteBookMark(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long hospitalId) {
        bookMarkService.deleteBookMark(customUser, hospitalId);
        return ResponseEntity.ok("북마크 삭제 성공");
    }


    @GetMapping
    public ResponseEntity<List<BookMarkDTO>> getBookMark(@AuthenticationPrincipal CustomUser customUser) {
        return ResponseEntity.ok(bookMarkService.findByUserId(customUser.getUser().getId()));
    }

}
