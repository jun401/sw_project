package com.example.sw.config;

import com.example.sw.model.User;
import com.example.sw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InitUserConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDefaultUser() {
        return args -> {
            if (userRepository.findByUsername("user") == null) {
                User user = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("1234"))
                        .build();
                userRepository.save(user);
            }
        };
    }
}
