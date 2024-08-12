package com.team5.hospital_here.common.security;


import com.team5.hospital_here.common.jwt.*;
import com.team5.hospital_here.user.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CorsConfig corsConfig;

    private final CustomOAuthUserDetailsService customOAuthUserDetailsService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;
    public SecurityConfig(CustomSuccessHandler customSuccessHandler, JwtUtil jwtUtil, CustomOAuthUserDetailsService customOAuthUserDetailsService) {
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.customOAuthUserDetailsService = customOAuthUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers("/error", "/favicon.ico");
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())); // NOTE : CORS 허용하는것
        http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/users").permitAll()

            .requestMatchers("/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
            .requestMatchers("/chatrooms/**", "/chat-messages/**").authenticated()
            .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
            .requestMatchers("/boards/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
            .requestMatchers("/posts/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
            .requestMatchers("/comments/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
            .anyRequest().permitAll()
        );
        http
            .oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                    .userService(customOAuthUserDetailsService))
                .successHandler(customSuccessHandler).failureUrl("/"));



        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
