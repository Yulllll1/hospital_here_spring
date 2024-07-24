package com.team5.hospital_here.user.service;


import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.user.entity.commonDTO.PasswordDTO;
import com.team5.hospital_here.user.entity.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.repository.LoginRepository;
import com.team5.hospital_here.user.entity.doctorEntity.DoctorProfileDTO;
import com.team5.hospital_here.user.repository.DoctorProfileRepository;
import com.team5.hospital_here.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final DoctorProfileRepository doctorProfileRepository;

    private final String USER_PASSWORD_ALTER_SUCCESSE = "비밀번호 변경을 완료했습니다.";

    /*
    public List<UserDTO> findAllUsers() {

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());

    }

    public UserDTO findUser(String email){
        try{
            UserDTO userDTO = UserMapper.toUserDTO(userRepository.findByEmail(email));
            return userDTO;
        }
        catch (Exception e)
        {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public void createUser(UserDTO userDTO) {

        if(userRepository.findByEmail(userDTO.getEmail()) != null)
        {
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        userRepository.save(UserMapper.toUserEntity(userDTO));

    }

    public void updateUserEmail(String email, String newEmail) {


        User user = userRepository.findByEmail(email);
        user.getLogin().setEmail(newEmail);
        userRepository.save(user);
    }
    public void updateUserAddress(String email, String putAddress)
    {
        User user = userRepository.findByEmail(email);
        user.setAddress(putAddress);
        userRepository.save(user);
    }
    public void updateUserPhone(String email, String putPhone)
    {
        User user = userRepository.findByEmail(email);
        user.setPhoneNumber(putPhone);
        userRepository.save(user);
    }

    public void updateUserPassword(String email, String validPassword, String putPassword, String putPassword2)
    {
        User user = userRepository.findByEmail(email);
        if(passwordEncoder.matches(validPassword,user.getLogin().getPassword()) && putPassword.equals(putPassword2))
        {
            user.getLogin().setPassword(passwordEncoder.encode(putPassword));
            userRepository.save(user);
        }
        else
        {
            throw new CustomException(ErrorCode.INVALID_USER_CREDENTIALS);
        }
    }

    public void deleteUser(String email){
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
    }



    public Boolean encodePassword(String password, UserDTO userDTO) {

        return passwordEncoder.matches(password, userDTO.getPassword());

    }

     */

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByLoginEmail(email);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public List<DoctorProfile> findAllDoctor(){
        return doctorProfileRepository.findAll();
    }

    public UserDTO findByUserEmail(String email) {
        return UserMapper.toUserDTO(userRepository.findByLoginEmail(email));
    }
    public UserDTO save(UserDTO userDTO) {
        User user = new User();
        user = UserMapper.toUserEntity(userDTO);
        user.setRole(Role.USER);
        String encodedPassword = encodePassword(userDTO.getPassword());
        user.getLogin().setPassword(encodedPassword);
        userRepository.save(user);

        return userDTO;
    }

    public UserDTO updateUser(String email, UserDTO userDTO) {
        User user = findUserByEmail(email);
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBirthday(Date.valueOf(userDTO.getBirthday()));
        user.setAddress(userDTO.getAddress());
        user.setImg(userDTO.getImage());
//        Login login = new Login();
//        login.setEmail(userDTO.getEmail());
//        login.setPassword(userDTO.getPassword());
//        login.setProvider(userDTO.getProvider());
//        login.setProviderId(userDTO.getProviderId());
//        user.setLogin(login);
//        user.setRole(Role.valueOf(userDTO.getRole()));

        return userDTO;
    }
    public String updateEmail(String email, UserDTO userDTO) {

        User user = findUserByEmail(email);
        Login login = user.getLogin();
        login.setEmail(userDTO.getEmail());
        loginRepository.save(login);
        return userDTO.getEmail();
    }
    public String updatePhone(String email, UserDTO userDTO)
    {
        User user = findUserByEmail(email);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userRepository.save(user);
        return userDTO.getPhoneNumber();
    }
    public String updatePassword(CustomUser customUser, PasswordDTO passwordDTO)
    {
        if(!passwordEncoder.matches(passwordDTO.getAlterPassword(), passwordDTO.getVerifyPassword()))
            throw new CustomException(ErrorCode.WRONG_PASSWORD);

        customUser.getUser().getLogin().setPassword(encodePassword(passwordDTO.getAlterPassword()));
        loginRepository.save(customUser.getUser().getLogin());

        return USER_PASSWORD_ALTER_SUCCESSE;
    }

    public String updateAddress(String email, UserDTO userDTO)
    {
        User user = findUserByEmail(email);
        user.setAddress(userDTO.getAddress());
        userRepository.save(user);
        return userDTO.getAddress();
    }
    public String deleteUser(String email) {
        User user = findUserByEmail(email);
        userRepository.delete(user);
        return "유저 삭제 완료";
    }

    public void deleteDoctorProfile(DoctorProfile doctorProfile){
        doctorProfileRepository.delete(doctorProfile);
    }

    /**
     * User Role 수정
     * 관리자 기능
     * @param id user id
     * @param updateRole 변경할 role의 string값
     * @return 수정된 user
     */
    public User updateUserRole(Long id, String updateRole){
        User user = findById(id);
        Role role = Role.valueOf(updateRole);

        if(role == Role.USER)
            doctorProfileRepository.findByUser(user).ifPresent(this::deleteDoctorProfile);

        user.setRole(role);

        userRepository.save(user);
        return user;
    }

    //TODO: Hospital CRUD 작업 완료 시, 수정 필요
    @Transactional
    public DoctorProfile createDoctorProfile(DoctorProfileDTO doctorProfileDTO){
        User user = updateUserRole(doctorProfileDTO.getUserId(), Role.DOCTOR.getName());

        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setUser(user);
        doctorProfile.setMajor(doctorProfileDTO.getMajor());

        return doctorProfileRepository.save(doctorProfile);
    }
}
