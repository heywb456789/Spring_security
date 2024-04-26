package org.example.spring_security_ex.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_security_ex.dto.JoinDTO;
import org.example.spring_security_ex.entity.UserEntity;
import org.example.spring_security_ex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {

//    @Autowired //필드 주입 방식
//    private UserRepository userRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder; // 주입 받아야 패스워드 부분을 처리 가능

    public void joinProcess(JoinDTO joinDTO){

        //필수 적으로 기본 디비에 유저가 저장되어 있는지 확인한다.
        boolean isExist = userRepository.existsByUsername(joinDTO.getUsername());
        if(isExist){
            return;
        }
        UserEntity user = UserEntity.builder()
                .username(joinDTO.getUsername())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userRepository.save(user);

    }
}
