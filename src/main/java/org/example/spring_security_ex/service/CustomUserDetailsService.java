package org.example.spring_security_ex.service;

import lombok.RequiredArgsConstructor;
import org.example.spring_security_ex.dto.CustomUserDetails;
import org.example.spring_security_ex.entity.UserEntity;
import org.example.spring_security_ex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * security UserDetails service
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // DataBase 에서 가져온 USerName 을 검증하는 로직
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username);

        if (user != null) {
            return new CustomUserDetails(user);
        }

        return null;
    }
}
