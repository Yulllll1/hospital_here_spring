package com.team5.hospital_here.common.jwt;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.socialDTO.GoogleResponse;
import com.team5.hospital_here.common.jwt.socialDTO.NaverResponse;
import com.team5.hospital_here.common.jwt.socialDTO.OAuth2Response;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuthUserDetailsService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;



    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Loading user {}", userRequest.getClientRegistration().getRegistrationId());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if(provider.equals("google"))
        {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else if(provider.equals("naver"))
        {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else
        {
            return null;
        }
        log.info("소셜 유저 if 로딩 성공");


        String providerId = oAuth2Response.getProviderId();
        String name = oAuth2Response.getName();
        String email = oAuth2Response.getEmail();
        String birthday = oAuth2Response.getBirthDay();
        String birthyear = oAuth2Response.getBirthYear();
        String birth = null;
        if(birthyear != null)
        {
            birth = birthyear + "-" + birthday;
        }
        String image = oAuth2Response.getImage();
        String phone = oAuth2Response.getPhoneNumber();
        log.info("소셜 유저 세팅 성공");
        log.info("이메일 : {}", email);
        Optional<User> optionalUser = userRepository.findByLoginEmail(email);

        if (optionalUser.isPresent()) {
            log.info("소셜 있음");
            User user = optionalUser.get();
            user.setName(name);
            userRepository.save(user);
            UserDTO userDTO = new UserDTO();
            userDTO.setName(name);
            userDTO.setImage(image);
            userDTO.setBirthday(birth);
            userDTO.setPhoneNumber(phone);
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setAddress(user.getAddress());
            userDTO.setAddressDetail(user.getAddressDetail());
            userDTO.setProvider(user.getLogin().getProvider());
            userDTO.setProviderId(user.getLogin().getProviderId());
            userDTO.setEmail(user.getLogin().getEmail());
            userDTO.setRole(user.getRole().toString());
            userDTO.setPassword(user.getLogin().getPassword());
            return new CustomOAuth2User(userDTO);

        } else {
            log.info("소셜 없음");
            if(birth.length() < 6 )
            {
                birth = "2000-01-01";
            }
            User user = null;

            user = createUser(email, name, provider, providerId, image, birth, phone);

            userRepository.save(user);
            UserDTO userDTO = new UserDTO();
            userDTO.setName(name);
            userDTO.setPhoneNumber(user.getPhoneNumber());
            userDTO.setAddress(user.getAddress());
            userDTO.setImage(image);
            userDTO.setBirthday(birth);
            userDTO.setPhoneNumber(phone);

            userDTO.setProvider(provider);
            userDTO.setProviderId(providerId);
            userDTO.setEmail(email);
            userDTO.setRole(Role.USER.toString());
            userDTO.setPassword(user.getLogin().getPassword());
            return new CustomOAuth2User(userDTO);
        }


    }
    private User createUser(String email, String name, String provider, String providerId, String image, String birth, String phone) {
        log.info("생일 : {}", birth);
        User user = new User();
        user.setName(name);
        user.setPhoneNumber(phone);
        user.setAddress("xxxx");
        user.setImg(image);
        user.setBirthday(Date.valueOf(birth));
        Login login =new Login();
        login.setEmail(email);
        login.setPassword("12345");
        login.setProvider(provider);
        login.setProviderId(providerId);
        user.setRole(Role.USER);
        user.setLogin(login);

        return user;
    }



}
