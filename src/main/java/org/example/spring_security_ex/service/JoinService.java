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

//    @Autowired //�ʵ� ���� ���
//    private UserRepository userRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder; // ���� �޾ƾ� �н����� �κ��� ó�� ����

    public void joinProcess(JoinDTO joinDTO){

        //�ʼ� ������ �⺻ ��� ������ ����Ǿ� �ִ��� Ȯ���Ѵ�.
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
