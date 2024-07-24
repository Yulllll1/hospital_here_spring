package com.team5.hospital_here.user.controller;


import com.team5.hospital_here.user.entity.LoginDTO;
import com.team5.hospital_here.user.service.LoginService;
import com.team5.hospital_here.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("토큰 인가 성공");
    }
}
