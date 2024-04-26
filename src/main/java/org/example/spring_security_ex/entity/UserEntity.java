package org.example.spring_security_ex.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "USER")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 의미없는 객체 생성 막기
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;

    @Builder
    public UserEntity (String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
