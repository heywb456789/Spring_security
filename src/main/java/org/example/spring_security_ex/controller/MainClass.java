package org.example.spring_security_ex.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainClass {

    @GetMapping("/")
    public String index(Model model) {

        //현재 사용자 아이디
        //기본적으로는 서비스 분리해서 사용한다.
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority grantedAuthority = iterator.next();
        String role = grantedAuthority.getAuthority();

        model.addAttribute("id", id );
        model.addAttribute("role",  role );


        return "main";
    }
}
