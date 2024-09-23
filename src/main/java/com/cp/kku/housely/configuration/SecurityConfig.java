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
                        .requestMatchers("/", "/home", "/auth/register", "/auth/login").permitAll() // อนุญาตให้เข้าถึงหน้าที่ระบุได้ทุกคน
                        .anyRequest().authenticated() // ทุก request ต้องมีการยืนยันตัวตน
                )
                .formLogin(form -> form
                        .loginPage("/auth/login") // หน้า login ที่เราจะสร้าง
                        .defaultSuccessUrl("/", true) // redirect ไปที่หน้า home หลังจาก login สำเร็จ
                        .permitAll() // อนุญาตให้เข้าถึงหน้า login ได้ทุกคน
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/auth/login?logout") // redirect หลังจาก logout
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ใช้ BCrypt สำหรับเข้ารหัสรหัสผ่าน
    }

}