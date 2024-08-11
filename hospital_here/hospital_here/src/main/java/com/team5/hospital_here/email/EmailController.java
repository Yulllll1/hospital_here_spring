package com.team5.hospital_here.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody Map<String, String> request) {

        String to = request.get("to");
        String subject = request.get("subject");
        String text = request.get("text");

        emailService.sendEmail(to, subject, text);
        return ResponseEntity.ok("이메일 발송 성공");

    }


}
