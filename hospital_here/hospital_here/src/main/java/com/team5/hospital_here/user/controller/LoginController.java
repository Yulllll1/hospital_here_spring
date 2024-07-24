package com.team5.hospital_here.user.controller;


import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.user.entity.login.LoginDTO;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        return ResponseEntity.ok(loginService.login(loginDTO, response));
    }

    @GetMapping("/role-test")
    public ResponseEntity<String> roleTest(){
        return ResponseEntity.ok("토큰 인가 성공");
    }

    @GetMapping("/logined-info-test")
    public ResponseEntity<User> loginedInfoTest(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(customUser.getUser());
    }
}
