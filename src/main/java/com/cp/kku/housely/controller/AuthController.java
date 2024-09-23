package com.cp.kku.housely.controller;

import com.cp.kku.housely.exception.UserNotFoundException;
import com.cp.kku.housely.model.User;
import com.cp.kku.housely.repository.UserRepository;
import com.cp.kku.housely.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login"; // ชื่อไฟล์ login.html
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register"; // ชื่อไฟล์ register.html
    }

    @PostMapping("/register")
    public String register(User user, Model model) {
        userService.register(user);
        return "redirect:/auth/login"; // redirect ไปหน้า login หลังลงทะเบียนสำเร็จ
    }
}
