package org.example.spring_security_ex.dto;

import org.example.spring_security_ex.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Data Base 에서 USer Name 을 가져와 인증을 하는 로직
 */
public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    //생성자
    public CustomUserDetails(UserEntity user) {
        this.userEntity = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    /**
     *  만료 되었다는 설정을 피하기 위해 일단 True
     *  isAccountNonExpired
     *  isAccountNonLocked
     *  isCredentialsNonExpired
     *  isEnabled
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
