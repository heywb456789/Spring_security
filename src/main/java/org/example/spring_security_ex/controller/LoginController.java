package org.example.spring_security_ex.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
