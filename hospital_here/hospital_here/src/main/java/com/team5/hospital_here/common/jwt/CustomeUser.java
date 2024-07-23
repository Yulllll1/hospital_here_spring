package com.team5.hospital_here.common.jwt;

import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomeUser extends User {

    private com.team5.hospital_here.user.entity.User user;

    public CustomeUser(com.team5.hospital_here.user.entity.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin().getEmail(), user.getLogin().getPassword(), authorities);
        this.user = user;
    }
}
