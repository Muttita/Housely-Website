package com.cp.kku.housely.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/", "/home", "/auth/register", "/auth/login").permitAll()
                            .requestMatchers("/static/**", "/uploads/**", "/css/**", "/js/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/auth/login")
                            .defaultSuccessUrl("/user/product", true)
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutSuccessUrl("/auth/login?logout")
                            .permitAll()
                    );
    
            return http.build();
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ใช้ BCrypt สำหรับเข้ารหัสรหัสผ่าน
    }
}
