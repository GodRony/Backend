package com.in28minutes.springboot.myfirstwebapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.function.Function;

@Configuration
public class SpringSecurityConfiguration {
    //LDAP or Database
    //In Memory
    //InMemoryUserDetailsManager

    @Bean
    public InMemoryUserDetailsManager createUserDetailManager() {
        PasswordEncoder encoder = passwordEncoder(); // BCrypt 인코더
        UserDetails userDetails = User.builder()
                .username("in28minutes")
                .password(encoder.encode("dummy")) // 비밀번호를 BCrypt로 암호화해서 저장
                .roles("USER", "ADMIN")
                .build();
//        UserDetails userDetails = User.withDefaultPasswordEncoder()
//                .username("in28minutes")
//                .password("dummy")
//                .roles("USER","ADMIN")
//                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 인코더 빈 등록
    }

}
