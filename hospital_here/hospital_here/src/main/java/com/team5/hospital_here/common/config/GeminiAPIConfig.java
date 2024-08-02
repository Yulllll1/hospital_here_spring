package com.team5.hospital_here.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class GeminiAPIConfig {

    @Bean
    public RestTemplate geminiTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors()
            .add(((request, body, execution) -> execution.execute(request, body)));
        return restTemplate;
    }

}
