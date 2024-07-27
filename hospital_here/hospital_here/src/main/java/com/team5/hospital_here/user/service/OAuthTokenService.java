package com.team5.hospital_here.user.service;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.JwtUtil;
import com.team5.hospital_here.common.jwt.entity.RefreshToken;
import com.team5.hospital_here.common.jwt.repository.RefreshTokenRepository;
import com.team5.hospital_here.user.entity.login.Login;
import com.team5.hospital_here.user.repository.LoginRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginRepository loginRepository;
    @Autowired
    public OAuthTokenService(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository, LoginRepository loginRepository) {
        this.jwtUtil = jwtUtil;
        this.loginRepository = loginRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }
    public String createAndSaveTokens(String email, HttpServletResponse response) {
        RefreshToken dbToken = refreshTokenRepository.findByLoginEmail(email).orElse(new RefreshToken());
        dbToken.setLogin(findByEmail(email));

        String accessToken = createToken(dbToken, email, response);
        return accessToken;
    }

    private Login findByEmail(String email){
        return loginRepository.findByEmail(email).orElseThrow(()->
                new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private String createToken(RefreshToken dbToken, String email, HttpServletResponse response){
        String accessToken = "Bearer " + jwtUtil.generateAccessToken(email);
        response.setHeader("Authorization", accessToken);

        String refreshToken = jwtUtil.generateRefreshToken(email);
        Cookie cookie = new Cookie(jwtUtil.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(jwtUtil.REFRESH_TOKEN_COOKIE_MAX_AGE);
        response.addCookie(cookie);

        dbToken.setToken(refreshToken);
        refreshTokenRepository.save(dbToken);
        return accessToken;
    }

}
