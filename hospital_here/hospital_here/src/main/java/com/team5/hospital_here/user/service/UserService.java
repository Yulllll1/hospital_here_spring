package com.team5.hospital_here.user.service;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.Login;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.User;
import com.team5.hospital_here.user.entity.UserDTO;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*public List<UserDTO> findAllUsers() {

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

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public UserDTO findByUserEmail(String email) {
        return UserMapper.toUserDTO(userRepository.findByEmail(email));
    }
    public UserDTO save(UserDTO userDTO) {
        User user = new User();
        user = UserMapper.toUserEntity(userDTO);
        userRepository.save(user);
        return userDTO;
    }
    public UserDTO updateUser(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email);
        user.setUserName(userDTO.getUserName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setBirthday(userDTO.getBirthDate());
        user.setAddress(userDTO.getAddress());
        user.setImage(userDTO.getImage());
        Login login = new Login();
        login.setEmail(userDTO.getEmail());
        login.setPassword(userDTO.getPassword());
        login.setProvider(userDTO.getProvider());
        login.setProviderKey(userDTO.getProviderKey());
        user.setLogin(login);
        user.setRole(userDTO.getRole());

        return userDTO;
    }
    public String updateEmail(String email, UserDTO userDTO) {
        User user = userRepository.findByEmail(email);
        user.getLogin().setEmail(userDTO.getEmail());

        return userDTO.getEmail();


    }
    public String updatePhone(String email, UserDTO userDTO)
    {
        User user = userRepository.findByEmail(email);
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userDTO.getPhoneNumber();
    }
    public String updatePassword(String email, UserDTO userDTO)
    {
        User user = userRepository.findByEmail(email);
        user.getLogin().setPassword(userDTO.getPassword());
        return userDTO.getPassword();
    }
    public String updateAddress(String email, UserDTO userDTO)
    {
        User user = userRepository.findByEmail(email);
        user.setAddress(userDTO.getAddress());
        return userDTO.getAddress();
    }
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
    }

    /**
     * User Role 수정
     * 관리자 기능
     * @param id user id
     * @param updateRole 변경할 role의 string값
     * @return 수정된 user
     */
    public User updateUserRole(Long id, String updateRole){
        User user = userRepository.findById(id).orElseThrow(()->
            new CustomException(ErrorCode.USER_NOT_FOUND));

        Role role = Role.valueOf(updateRole);
        user.setRole(role);

        userRepository.save(user);
        return user;
    }
}
