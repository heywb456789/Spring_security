package org.example.spring_security_ex.controller;

import org.example.spring_security_ex.dto.JoinDTO;
import org.example.spring_security_ex.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        //성공시 로그인 실패시 다시 회원가입

        System.out.println(joinDTO.toString());

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
