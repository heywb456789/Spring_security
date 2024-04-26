package org.example.spring_security_ex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * ������ ��ܺ� ���� ���ʴ�� ���۵Ǳ� ������ ���� ������ �����Ѵ�. 
         * .permitAll() ��ο��� 
         * .hasRole() ���� �ܼ�  .hasAnyRole() ���� ����
         * .authenticated() �α��� �� ������
         * 2.x ~ 2.6.x  WebSecurityCofigurerAdapter ��� �޾�  Configuer �������̵� �Ͽ� ��� 
         * 2.7 : SecurityFilterChain InterFace �� Bean ���� ����Ͽ� ���
         * ��Ʈ 3.1 ���ʹ� ���ٸ� ����ؾ��Ѵ�.
         */
        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/", "/login").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                );

        return http.build();

    }

}
