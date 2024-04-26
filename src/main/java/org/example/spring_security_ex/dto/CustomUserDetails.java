package org.example.spring_security_ex.dto;

import org.example.spring_security_ex.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Data Base ���� USer Name �� ������ ������ �ϴ� ����
 */
public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    //������
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
     *  ���� �Ǿ��ٴ� ������ ���ϱ� ���� �ϴ� True
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
