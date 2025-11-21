package com.example.sw.controller;

import com.example.sw.model.User;
import com.example.sw.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    // ✔ 변경됨 : POST 요청 URL → /doSignup
    @PostMapping("/doSignup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String name,
                         @RequestParam String email,
                         @RequestParam String gender,
                         @RequestParam String birth) {

        // 아이디 중복 확인
        if (userRepository.findByUsername(username) != null) {
            return "redirect:/signup?error=exists";
        }

        // 새 사용자 저장
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .email(email)
                .gender(gender)
                .birth(birth)
                .build();

        userRepository.save(user);

        // 회원가입 성공 → 로그인 페이지로 이동
        return "redirect:/login?signupSuccess=true";
    }
}
