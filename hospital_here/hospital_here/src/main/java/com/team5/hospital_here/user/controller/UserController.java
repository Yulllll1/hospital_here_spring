package com.team5.hospital_here.user.controller;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.User;
import com.team5.hospital_here.user.entity.UserDTO;
import com.team5.hospital_here.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.getUsernameFromToken(token.substring(7));
        UserDTO user = userService.findUser(email);
        if (user.getRole() != Role.ADMIN)
        {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }




    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {

        if(!userDTO.getProvider().isEmpty())
        {
            userDTO.setProvider(userDTO.getProvider());
            userDTO.setProviderKey(userDTO.getProviderKey());
        }
        userDTO.setUserId(null);
        userDTO.setRole(Role.USER);
        try {
            userService.createUser(userDTO);
            return ResponseEntity.ok(userDTO);
        }catch (CustomException e) {
            throw e;
        }
    }

}
