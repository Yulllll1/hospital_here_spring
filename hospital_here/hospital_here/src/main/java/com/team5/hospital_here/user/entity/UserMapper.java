package com.team5.hospital_here.user.entity;

import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;

public class UserMapper {

    @NotNull
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user);
    }

    @NotNull
    public static User toUserEntity(UserDTO userDTO) {
        Login login = new Login();
        login.setEmail(userDTO.getEmail());
        login.setPassword(userDTO.getPassword());
        login.setProvider(userDTO.getProvider());
        login.setProviderId(userDTO.getProviderId());

        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        Date.valueOf(userDTO.getBirthday());
        user.setAddress(userDTO.getAddress());
        user.setAddressDetail((userDTO.getAddressDetail()));
        user.setBirthday(Date.valueOf(userDTO.getBirthday()));
        user.setImg(userDTO.getImage());
        user.setLogin(login);

        return user;
    }
}
