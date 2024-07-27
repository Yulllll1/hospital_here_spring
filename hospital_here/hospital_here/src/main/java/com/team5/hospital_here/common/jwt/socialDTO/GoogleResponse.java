package com.team5.hospital_here.common.jwt.socialDTO;

import java.sql.Date;
import java.util.Map;

public class GoogleResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }
    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getImage() {
        return attribute.get("picture").toString();
    }

    @Override
    public String getPhoneNumber() {
        return "010-0000-0000";
    }

    @Override
    public String getBirthDay() {
        return null;
    }

    @Override
    public String getBirthYear() {
        return "";
    }
}
