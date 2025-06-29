package uz.dev.hmsproject.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

/**
 Created by: asrorbek
 DateTime: 28/06/25 21:06
 **/
@Configuration
public class CorsConfig {

    @Bean
    public CorsConfiguration corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");//domenlar
        configuration.addAllowedHeader("*");//header
        configuration.addAllowedMethod("*");//GET, POST
        return configuration;
    }

}
