package com.team5.hospital_here.user.controller;

import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.user.entity.user.address.AddressDTO;
import com.team5.hospital_here.user.entity.commonDTO.PasswordDTO;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileDTO;
import com.team5.hospital_here.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /*@GetMapping("/")
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
    */

    //모든 유저 가져오기
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    //모든 의사 가져오기
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorProfile>> getAllDoctors(){
        return ResponseEntity.ok(userService.findAllDoctor());
    }

    //특정 유저 가져오기(이메일로)
    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByUserEmail(email));
    }

    //유저 업데이트 (전체)
    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String email, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(email, userDTO));
    }
    //유저 업데이트 (이메일만)
    @PutMapping("/{email}/email")
    public ResponseEntity<String> updateUserEmail(@PathVariable String email, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateEmail(email, userDTO));
    }
    //유저 업데이트 (전화번호만)
    @PutMapping("/{email}/phone")
    public ResponseEntity<String> updatePhoneNumber(@PathVariable String email, @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updatePhone(email, userDTO));
    }

    //유저 삭제
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        return ResponseEntity.ok(userService.deleteUser(email));
    }

    //NOTE: ### 회원 기능 ####

    //마이페이지 정보 요청
    @GetMapping("my-page")
    public ResponseEntity<UserDTO> getMyPage(@AuthenticationPrincipal CustomUser customUser){
        return ResponseEntity.ok(UserMapper.toUserDTO(customUser.getUser()));
    }

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

    //NOTE: #### 관리자 기능 #####

    //사용자 권한 변경 요청
    @PutMapping("/{id}/{updateRole}")
    public ResponseEntity<User> updateUserRole(@PathVariable Long id, @PathVariable String updateRole){
        return ResponseEntity.ok(userService.updateUserRole(id, updateRole));
    }

    //의사 사용자 모든 정보 요청
    @PostMapping("/doctors")
    public ResponseEntity<DoctorProfile> createDoctorProfile(@RequestBody @Valid DoctorProfileDTO doctorProfileDTO){
        return new ResponseEntity<>(userService.createDoctorProfile(doctorProfileDTO), HttpStatus.CREATED);
    }
}
