package com.team5.hospital_here.user.controller;


import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.user.entity.UserDTO;
import com.team5.hospital_here.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verified/users")
public class VerifiedUserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public VerifiedUserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/my-page")
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsernameFromToken(token);

        UserDTO userDTO = userService.findUser(email);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/my-page")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsernameFromToken(token);
        userService.deleteUser(email);
        return ResponseEntity.ok("유저 삭제 성공");
    }

    @PutMapping("/my-page/email")
    public ResponseEntity<String> updateUserEmail(HttpServletRequest request, @RequestParam String putEmail) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsernameFromToken(token);
        userService.updateUserEmail(email, putEmail);
        return ResponseEntity.ok("이메일 변경 성공");


    }

    @PutMapping("/my-page/password")
    public ResponseEntity<String> updateUserPassword(HttpServletRequest request,
                                                     @RequestParam String validPassword,
                                                     @RequestParam String putPassword,
                                                     @RequestParam String putPassword2) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsernameFromToken(token);
        try {
            userService.updateUserPassword(email,validPassword,putPassword,putPassword2);
            return ResponseEntity.ok("비밀번호 변경 성공");
        }catch (CustomException e) {
            throw e;
        }

    }

    @PutMapping("/my-page/address")
    public ResponseEntity<String> updateUserAddress(HttpServletRequest request,
                                                    @RequestParam String putAddress)
    {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsernameFromToken(token);
        userService.updateUserAddress(email,putAddress);
        return ResponseEntity.ok("주소 변경 성공");
    }

    @PutMapping("/my-page/phone")
    public ResponseEntity<String> updateUserPhone(HttpServletRequest request,
                                                  @RequestParam String putPhone)
    {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.getUsernameFromToken(token);
        userService.updateUserPhone(email,putPhone);

        return ResponseEntity.ok("전화번호 변경 성공");
    }

}
