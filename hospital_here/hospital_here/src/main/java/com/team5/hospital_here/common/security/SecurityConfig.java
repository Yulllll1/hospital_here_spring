package com.team5.hospital_here.common.security;


import com.team5.hospital_here.common.jwt.CustomUserDetailsService;
import com.team5.hospital_here.common.jwt.JwtAuthenticationFilter;
import com.team5.hospital_here.user.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource())); // NOTE : CORS 허용하는것

        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers("/refresh-token").permitAll()

            .requestMatchers("/role-test", "/logined-info-test").hasRole(Role.USER.name())
            .requestMatchers("/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
            .requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
            .anyRequest().permitAll()
        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
