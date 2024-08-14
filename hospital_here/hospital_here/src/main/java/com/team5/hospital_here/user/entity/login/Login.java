package com.team5.hospital_here.user.entity.login;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "login")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "이메일을 입력해 주세요.")
    @Email(message = "옮바른 이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "verified")
    private String verified;

    @Column(name = "status")
    private String status;



}
