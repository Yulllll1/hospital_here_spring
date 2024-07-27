package com.team5.hospital_here.common.jwt.socialDTO;


import java.sql.Date;

public interface OAuth2Response {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
    String getImage();
    String getPhoneNumber();
    String getBirthDay();
    String getBirthYear();
}
