package org.example.spring_security_ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainClass {

    @GetMapping("/")
    public String index() {
        return "main";
    }
}
