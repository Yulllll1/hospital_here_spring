package com.team5.hospital_here.user.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {

    private Long userId;
    private String email;
    private String userName;
    private String password;
    private String phoneNumber;
    private Date birthDate;
    private String address;
    private String image;
    private String provider;
    private String providerKey;
    private Role role;

    public UserDTO(User user){
        this.userId = user.getUserId();
        this.email = user.getLogin().getEmail();
        this.userName = user.getUserName();
        this.password = user.getLogin().getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthday();
        this.address = user.getAddress();
        this.image = user.getImage();
        this.provider = user.getLogin().getProvider();
        this.providerKey = user.getLogin().getProviderKey();
        this.role = user.getRole();
    }


}
