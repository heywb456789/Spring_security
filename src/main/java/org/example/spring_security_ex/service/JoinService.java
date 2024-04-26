package org.example.spring_security_ex.service;

import org.example.spring_security_ex.dto.JoinDTO;
import org.example.spring_security_ex.entity.UserEntity;
import org.example.spring_security_ex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired //�ʵ� ���� ���
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ���� �޾ƾ� �н����� �κ��� ó�� ����

    public void joinProcess(JoinDTO joinDTO){

        //�ʼ� ������ �⺻ ��� ������ ����Ǿ� �ִ��� Ȯ���Ѵ�.
        userRepository.existsByUsername(joinDTO.getUserName());

        UserEntity user = UserEntity.builder()
                .username(joinDTO.getUserName())
                .password(passwordEncoder.encode(joinDTO.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);

    }
}
