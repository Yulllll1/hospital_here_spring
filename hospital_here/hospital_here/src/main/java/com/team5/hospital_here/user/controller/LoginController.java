package com.team5.hospital_here.user.controller;


import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.user.entity.login.LoginDTO;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserResponseDTO;
import com.team5.hospital_here.user.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        return ResponseEntity.ok(loginService.login(loginDTO, response));
    }

    @PostMapping("/user-logout")
    public void logout(@CookieValue String refreshToken, HttpServletResponse response){
        loginService.logout(refreshToken, response);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@CookieValue String refreshToken, HttpServletResponse response){
        return ResponseEntity.ok(loginService.createNewAccessToken(refreshToken, response));
    }

    @PostMapping("/email-verified")
    public ResponseEntity<String> emailVerified(@RequestBody Map<String, String> request){
        String email = request.get("email");
        loginService.verified(email);
        return ResponseEntity.ok("인증번호 발송 성공");
    }

    @PostMapping("/password-reset")
    public ResponseEntity<String> passwordReset(@RequestBody Map<String, String> request)
    {
        String verified = request.get("verified");
        loginService.resetPassword(verified);
        return ResponseEntity.ok("비밀번호 리셋 성공");
    }

    @PostMapping("/email-search")
    public ResponseEntity<String> emailSearch(@RequestBody Map<String, String> request)
    {
        String userName = request.get("userName");
        String phoneNumber = request.get("phoneNumber");

        return ResponseEntity.ok(loginService.emailFind(userName, phoneNumber));
    }
}
