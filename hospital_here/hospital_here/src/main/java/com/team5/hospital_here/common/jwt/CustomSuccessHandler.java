package com.team5.hospital_here.common.jwt;

import com.team5.hospital_here.common.jwt.repository.RefreshTokenRepository;
import com.team5.hospital_here.user.service.LoginService;
import com.team5.hospital_here.user.service.OAuthTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final OAuthTokenService oAuthTokenService;


    public CustomSuccessHandler(JwtUtil jwtUtil, OAuthTokenService oAuthTokenService) {
        this.jwtUtil = jwtUtil;
        this.oAuthTokenService = oAuthTokenService;


    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = customOAuth2User.getUserEmail();

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
//        GrantedAuthority authority = iterator.next();
//        String role = authority.getAuthority();


//        Map<String, Object> responseBody = loginService.createTokenForSocialLogin(email, response);
//        String token = responseBody.get("token").toString();
//        Long userId = Long.parseLong(responseBody.get("userId").toString());


        Map<String, Object> tokenData = oAuthTokenService.createAndSaveTokens(email, response);
        String token = tokenData.get("token").toString();
        Long userId = Long.parseLong(tokenData.get("userId").toString());
        String role = tokenData.get("role").toString();
        response.addHeader("Authorization", "Bearer " + token);

//        String refreshToken = jwtUtil.generateRefreshToken(email);
//        Cookie cookie = new Cookie(jwtUtil.REFRESH_TOKEN_COOKIE_NAME, refreshToken);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        cookie.setMaxAge(jwtUtil.REFRESH_TOKEN_COOKIE_MAX_AGE);
//        response.addCookie(cookie);


        response.sendRedirect("http://localhost:3000/social-login-success?token=" + token + "&userId=" + userId + "&role=" + role);
        //response.sendRedirect("http://localhost:3000/social-login-success?token=" + token);

    }
}
