package com.team5.hospital_here.common.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.exception.ExceptionResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    @Lazy
    private CustomOAuthUserDetailsService customOAuthUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("로그인 접근1");
        String accessToken = getJwtFromRequest(request);
        log.info("로그인 접근11");

        if (accessToken == null) {
            log.info("로그인 접근111");
            filterChain.doFilter(request, response);
            log.info("로그인 접근1111");
            return;
        } else if(!jwtUtil.validateAccessToken(accessToken)){
            log.info("로그인 접근11111");
            jwtExceptionHandler(response, ErrorCode.ACCESS_TOKEN_EXPIRED);
            log.info("로그인 접근111111");
            return;
        }

        accessToken(accessToken, request);

        filterChain.doFilter(request, response);
    }

    public void jwtExceptionHandler(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getCode());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new ExceptionResponse(errorCode));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void accessToken(String token, HttpServletRequest request){
        String email = jwtUtil.getEmailFromAccessToken(token);
        try{
               CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(email);
               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                       user, null, List.of(new SimpleGrantedAuthority(user.getUser().getRole().getName())));

               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
           } catch (CustomException e) {
            log.info("소셜 사용자 로그인");
               CustomOAuth2User oAuth2User = customOAuthUserDetailsService.loadUserByEmail(email);
               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                       oAuth2User, null, List.of(new SimpleGrantedAuthority(oAuth2User.getUser().getRole().getName())));
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
