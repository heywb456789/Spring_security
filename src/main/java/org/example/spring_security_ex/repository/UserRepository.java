package org.example.spring_security_ex.repository;

import org.example.spring_security_ex.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String userName);

    UserEntity findByUsername(String username);
}
