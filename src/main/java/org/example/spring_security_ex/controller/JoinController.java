package org.example.spring_security_ex.controller;

import lombok.RequiredArgsConstructor;
import org.example.spring_security_ex.dto.JoinDTO;
import org.example.spring_security_ex.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        //������ �α��� ���н� �ٽ� ȸ������

        System.out.println(joinDTO.toString());

        joinService.joinProcess(joinDTO);

        return "redirect:/main";
    }
}
