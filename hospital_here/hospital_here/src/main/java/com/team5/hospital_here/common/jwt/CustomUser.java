package com.team5.hospital_here.common.jwt;

import java.util.Collection;
import java.util.Map;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomUser extends User implements OAuth2User{

    private com.team5.hospital_here.user.entity.user.User user;

    public CustomUser(com.team5.hospital_here.user.entity.user.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin().getEmail(), user.getLogin().getPassword(), authorities);
        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
