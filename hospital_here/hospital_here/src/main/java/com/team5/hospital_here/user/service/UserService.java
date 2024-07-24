package com.team5.hospital_here.user.service;


import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.user.entity.user.address.AddressDTO;
import com.team5.hospital_here.user.entity.commonDTO.PasswordDTO;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.entity.user.phoneNumberDTO.PhoneNumberDTO;
import com.team5.hospital_here.user.repository.LoginRepository;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileDTO;
import com.team5.hospital_here.user.repository.DoctorProfileRepository;
import com.team5.hospital_here.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    private final String USER_DELETED_SUCESS = "회원 탈퇴를 완료했습니다.";
    private final String DOCTOR_PROFILE_DELETED_SUCESS = "의사 프로필 삭제를 완료했습니다.";

    /**
     * 패스워드를 암호화 합니다.
     * @param password 암호화할 패스워드
     * @return 암호화된 패스워드
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 이미 존재하는 회원인지 검증합니다.
     * @param user 검증할 회원
     * @exception CustomException 이미 존재하는 이메일 또는 이름
     */
    private void verifyExistUser(User user){
        verifyExistEmail(user.getLogin().getEmail());
        verifyExistUserName(user.getName());
    }

    /**
     * 이미 존재하는 이메일인지 검증합니다.
     * @param email 검증할 이메일
     * @exception CustomException 이미 존재하는 이메일
     */
    private void verifyExistEmail(String email){
        loginRepository.findByEmail(email).ifPresent((login)->{
            throw new CustomException(ErrorCode.USER_NAME_ALREADY_EXISTS);
        });
    }

    /**
     * 이미 존재하는 이름인지 검증합니다.
     * @param name 검증할 이름
     * @exception CustomException 이미 존재하는 이름
     */
    private void verifyExistUserName(String name){
        userRepository.findByName(name).ifPresent((user)->{
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        });
    }

    /**
     * 이메일로 회원을 검색합니다.
     * @param email 검색에 사용할 이메일
     * @return 검색한 회원
     * @exception CustomException 존재하지 않는 회원
     */
    public User findUserByEmail(String email) {
        return userRepository.findByLoginEmail(email).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 데이터베이스 내에 모든 회원을 검색 후,
     * UserDTO List 형태로 반환합니다.
     * @return 검색된 모든 회원
     */
    public List<UserDTO> findAllToUserDTOList() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    /**
     * id값으로 회원을 검색합니다.
     * @param id 검색할 id
     * @return 검색된 회원
     * @exception CustomException 존재하지 않는 회원
     */
    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * id값으로 회원을 검색 한 후,
     * UserDTO 형태로 반환합니다.
     * @param id 검색할 id
     * @return 검색된 회원
     * @exception CustomException 존재하지 않는 회원
     */
    public UserDTO findUserByIdToUserDTO(Long id){
        return UserMapper.toUserDTO(findUserById(id));
    }

    /**
     * 데이터베이스 내에 모든 의사를 검색합니다.
     * @return 검색된 모든 의사
     */
    public List<DoctorProfile> findAllDoctor(){
        return doctorProfileRepository.findAll();
    }

    /**
     * id로 의사를 검색합니다.
     * @param id 검색할 id
     * @return 검색된 의사
     * @exception CustomException 존재하지 않는 회원
     */
    public DoctorProfile findDoctorById(Long id){
        return doctorProfileRepository.findById(id).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 회원 정보로 의사를 검색합니다.
     * @param user 검색할 회원 정보
     * @return 검색된 의사
     * @exception CustomException 존재하지 않는 회원
     */
    public DoctorProfile findDoctorByUser(User user){
        return doctorProfileRepository.findByUser(user).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 새로운 회원의 이메일 및 이름을 검증 후,
     * 회원을 등록합니다.
     * @param userDTO 저장할 회원 정보
     * @return 저장된 회원 정보
     * @exception CustomException 이미 사용중인 이메일 또는 이름
     */
    public UserDTO save(UserDTO userDTO) {
        User user = UserMapper.toUserEntity(userDTO);
        verifyExistUser(user);

        user.setRole(Role.USER);
        String encodedPassword = encodePassword(userDTO.getPassword());
        user.getLogin().setPassword(encodedPassword);
        userRepository.save(user);

        return userDTO;
    }

    /**
     * 회원 정보를 수정합니다.
     * @param id 수정할 회원의 id
     * @param userDTO 새로운 회원 정보
     * @return 수정된 회원 정보
     * @exception CustomException 존재하지 않는 회원
     */
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO){
        User user = findUserById(id);
        return updateUser(user, userDTO);
    }

    /**
     * 회원 정보를 수정합니다.
     * @param user 수정할 회원 정보
     * @param userDTO 새로운 회원 정보
     * @return 수정된 회원 정보
     */
    public UserDTO updateUser(User user, UserDTO userDTO){
        user.update(userDTO);
        return UserMapper.toUserDTO(userRepository.save(user));
    }

    /**
     * 로그인한 회원의 연락처를 수정합니다.
     * @param customUser 로그인한 회원
     * @param phoneNumberDTO 새로운 연락처
     * @return 수정된 연락처
     */
    public PhoneNumberDTO updatePhone(CustomUser customUser, PhoneNumberDTO phoneNumberDTO)
    {
        User user = customUser.getUser();
        user.update(phoneNumberDTO);
        userRepository.save(user);
        return phoneNumberDTO;
    }

    /**
     * 로그인한 회원의 패스워드를 수정합니다.
     * @param customUser 로그인한 회원
     * @param passwordDTO 기존 패스워드 및 새로운 패스워드
     * @return 패스워드 수정 완료
     * @exception CustomException 기존 패스워드 매치 실패
     */
    public String updatePassword(CustomUser customUser, PasswordDTO passwordDTO)
    {
        User user = customUser.getUser();
        if(!passwordEncoder.matches(passwordDTO.getVerifyPassword(), user.getLogin().getPassword()))
            throw new CustomException(ErrorCode.WRONG_PASSWORD);

        user.getLogin().setPassword(encodePassword(passwordDTO.getAlterPassword()));
        loginRepository.save(user.getLogin());

        return USER_PASSWORD_ALTER_SUCCESSE;
    }

    /**
     * 호그인한 회원의 주소를 수정합니다.
     * @param customUser 로그인한 회원
     * @param addressDTO 새로운 주소
     * @return 수정된 주소
     */
    public AddressDTO updateAddress(CustomUser customUser, AddressDTO addressDTO)
    {
        User user = customUser.getUser();
        user.update(addressDTO);
        userRepository.save(user);

        return addressDTO;
    }

    /**
     * 로그인한 회원의 회원탈퇴를 진행합니다.
     * @param customUser 로그인한 회원
     * @return 회원 탈퇴 성공
     */
    public String deleteUser(CustomUser customUser) {
        User user = customUser.getUser();
        return deleteUser(user);
    }

    /**
     * id를 통해 검색된 회원을 삭제합니다.
     * @param id 검색할 id
     * @return 회원 삭제 성공
     * @exception CustomException 존재하지 않는 회원
     */
    public String deleteUser(Long id){
        User user = findUserById(id);
        return deleteUser(user);
    }

    /**
     * 회원을 삭제합니다.
     * @param user 삭제할 회원
     * @return 삭제 성공
     */
    public String deleteUser(User user){
        userRepository.delete(user);
        return USER_DELETED_SUCESS;
    }

    /**
     * 의사 프로필을 삭제합니다.
     * @param doctorProfile 삭제할 의사 프로필
     */
    public String deleteDoctorProfile(DoctorProfile doctorProfile){
        doctorProfileRepository.delete(doctorProfile);
        return DOCTOR_PROFILE_DELETED_SUCESS;
    }

    /**
     * id로 검색된 회원 권한을 수정합니다.
     * 해당 회원에게 의사 프로필이 있다면,
     * 의사 프로필을 삭제합니다.
     * @param id 검색할 회원 id
     * @param updateRole 새로운 권한
     * @return 수정된 회원
     * @exception CustomException 존재하지 않는 회원
     */
    public UserDTO updateUserRole(Long id, String updateRole){
        User user = findUserById(id);
        Role role = Role.valueOf(updateRole);

        switch (role){
            case USER:
                doctorProfileRepository.findByUser(user).ifPresent(this::deleteDoctorProfile);
                user = updateUserRoleToUSER(user);
                break;

            case DOCTOR:
                user = updateUserRoleToDOCTOR(user);
                break;
        }

        user.setRole(role);

        return UserMapper.toUserDTO(userRepository.save(user));
    }

    /**
     * 회원의 권한을 일반 회원으로 수정합니다.
     * @param user 수정할 회원
     * @return 수정된 회원
     */
    private User updateUserRoleToUSER(User user){
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    /**
     * 회원의 권한을 의사로 수정합니다.
     * @param user 수정할 회원
     * @return 수정된 회원
     */
    private User updateUserRoleToDOCTOR(User user){
        user.setRole(Role.DOCTOR);
        return userRepository.save(user);
    }

    /**
     * 회원의 권한을 관리자로 변경합니다.
     * @param user 수정할 회원
     * @return 수정된 회원
     */
    private User updateUserRoleToADMIN(User user){
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }

    //TODO: Hospital Entity 추가 되면 작업
    @Transactional
    public DoctorProfile createDoctorProfile(DoctorProfileDTO doctorProfileDTO){
        User user = findUserById(doctorProfileDTO.getUserId());
        doctorProfileRepository.findByUser(user).ifPresent((doctorProfile)->{
            throw new CustomException(ErrorCode.ALREADY_DOCTOR_USER);
        });

        user = updateUserRoleToDOCTOR(user);

        DoctorProfile doctorProfile = new DoctorProfile();
        doctorProfile.setUser(user);
        doctorProfile.setMajor(doctorProfileDTO.getMajor());

        return doctorProfileRepository.save(doctorProfile);
    }
}
