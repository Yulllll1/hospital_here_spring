package com.team5.hospital_here.user.controller;

import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.user.entity.user.address.AddressDTO;
import com.team5.hospital_here.user.entity.commonDTO.PasswordDTO;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.user.phoneNumberDTO.PhoneNumberDTO;
import com.team5.hospital_here.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //마이페이지 정보 요청
    @GetMapping("/my-page")
    public ResponseEntity<UserDTO> getMyPage(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(UserMapper.toUserDTO(customUser.getUser()));
    }

    //TODO: Hospital Entity 추가 되면 작업
    //@GetMapping("/my-page/doctor-profile")


    //회원 가입
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.save(userDTO));
    }

    //회원 비밀번호 변경
    @PutMapping("/my-page/password")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal CustomUser customUser,
                                                    @RequestBody @Valid PasswordDTO passwordDTO) {
        return ResponseEntity.ok(userService.updatePassword(customUser, passwordDTO));
    }

    //회원 주소 변경
    @PutMapping("/my-page/address")
    public ResponseEntity<AddressDTO> updateAddress(@AuthenticationPrincipal CustomUser customUser,
                                                    @RequestBody @Valid AddressDTO addressDTO) {
        return ResponseEntity.ok(userService.updateAddress(customUser, addressDTO));
    }

    //회원 연락처 변경
    @PutMapping("/my-page/phoneNumber")
    public ResponseEntity<PhoneNumberDTO> updatePhoneNumber(@AuthenticationPrincipal CustomUser customUser,
                                                    @RequestBody @Valid PhoneNumberDTO phoneNumberDTO) {
        return ResponseEntity.ok(userService.updatePhone(customUser, phoneNumberDTO));
    }

    //회원 탈퇴
    @DeleteMapping("/my-page/delete")
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(userService.deleteUser(customUser));
    }
}
