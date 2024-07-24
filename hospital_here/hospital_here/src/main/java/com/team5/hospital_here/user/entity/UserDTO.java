package com.team5.hospital_here.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private String birthDate;
    private String address;
    private String addressDetail;
    private String image;
    private String provider;
    private String providerId;
    private String role;

    public UserDTO(User user){
        this.id = user.getId();
        this.email = user.getLogin().getEmail();
        this.name = user.getName();
        this.password = user.getLogin().getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.birthDate = user.getBirthday().toString();
        this.address = user.getAddress();
        this.addressDetail = user.getAddressDetail();
        this.image = user.getImg();
        this.provider = user.getLogin().getProvider();
        this.providerId = user.getLogin().getProviderId();
        this.role = user.getRole().name();
    }
}
