package com.team5.hospital_here.user.controller;

import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileDTO;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileResponseDTO;
import com.team5.hospital_here.user.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    //모든 회원 정보 요청
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        return ResponseEntity.ok(userService.findAllToUserDTOList());
    }

    //모든 의사 정보 요청
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorProfileResponseDTO>> getAllDoctor(){
        return ResponseEntity.ok(userService.findAllDoctorToResponseDTO());
    }

    //특정 회원 정보 요청
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserByIdToUserDTO(id));
    }

    //특정 회원 정보 변경 요청
    @PostMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserDTO userDTO){
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    //특정 회원 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    //회원 권한 변경 요청
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestParam String updateRole){
        return ResponseEntity.ok(userService.updateUserRole(id, updateRole));
    }

    //일반 회원을 의사 회원으로 변경 요청
    @PostMapping("/doctors")
    public ResponseEntity<DoctorProfile> createDoctorProfile(@RequestBody @Valid DoctorProfileDTO doctorProfileDTO){
        return new ResponseEntity<>(userService.createDoctorProfile(doctorProfileDTO), HttpStatus.CREATED);
    }
}
