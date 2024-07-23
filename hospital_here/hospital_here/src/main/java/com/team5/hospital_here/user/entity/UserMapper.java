package com.team5.hospital_here.user.entity;

import jakarta.validation.constraints.NotNull;

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
        user.setId(userDTO.getUserId());
        user.setName(userDTO.getUserName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
       // user.setBirthday(userDTO.getBirthDate());
        user.setAddress(userDTO.getAddress());
        user.setImg(userDTO.getImage());
        user.setLogin(login);
        user.setRole(userDTO.getRole());

        return user;

    }
}
