package com.team5.hospital_here.common.jwt.socialDTO;

import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.Map;

public class NaverResponse implements OAuth2Response{

    private final Map<String, Object> attributes;

    public NaverResponse(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getImage(){
        return attributes.get("profile_image").toString();
    }

    @Override
    public String getPhoneNumber() {
        return attributes.get("mobile").toString();
    }

    @Override
    public String getBirthDay() {
        return attributes.get("birthday").toString();
    }
}
