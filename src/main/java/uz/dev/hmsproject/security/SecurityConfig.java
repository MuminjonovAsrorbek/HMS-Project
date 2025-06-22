package uz.dev.hmsproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.dev.hmsproject.handler.SecurityAccessExceptionHandler;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final SecurityAccessExceptionHandler securityAccessExceptionHandler;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTFilter filter) throws Exception {
//
//        http.csrf(AbstractHttpConfigurer::disable);
//        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        http.sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.authorizeHttpRequests(conf -> conf
//                .requestMatchers("/auth/**")
//                .permitAll()
//
//                .anyRequest()
//                .authenticated()
//        );
//        http.exceptionHandling(conf->conf
//                .authenticationEntryPoint(securityAccessExceptionHandler)
//        );
//        return http.build();
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
