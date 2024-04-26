package org.example.spring_security_ex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * �α��ν� ��й�ȣ�� ���� �ܹ��� �ؽ� ��ȣȭ�� �����Ͽ� ����Ǿ� �ִ� ��й�ȣ�� �����Ѵ�.
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(); // �����ڸ� �����Ͽ� �ڵ����� �����ǵ��� �Ѵ�.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * ������ ��ܺ� ���� ���ʴ�� ���۵Ǳ� ������ ���� ������ �����Ѵ�. 
         * .permitAll() ��ο��� 
         * .hasRole() ���� �ܼ�  .hasAnyRole() ���� ����
         * .authenticated() �α��� �� ������
         * 2.x ~ 2.6.x  WebSecurityCofigurerAdapter ��� �޾�  Configuer �������̵� �Ͽ� ���
         * 2.7 : SecurityFilterChain InterFace �� Bean ���� ����Ͽ� ���
         * 3.1  ���ʹ� ���ٸ� ����ؾ��Ѵ�.
         *      �ڵ����� csrf ��ϵǾ��ִ�.
         *      �α��ν� ��й�ȣ�� ���� �ܹ��� �ؽ� ��ȣȭ�� �����Ͽ� ����Ǿ� �ִ� ��й�ȣ�� �����Ѵ�.
         *      BCrypt Password Encoder �� �����ϰ�  �̸� @Bean ���� ����Ͽ� ���
         *      ����� : ��ĪŰ , ���ĪŰ
         *      �ܹ��� : �ؽ�
         */
        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/", "/login").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                );
        http
                .formLogin(
                        (auth) -> auth
                                .loginPage("/login")
                                .loginProcessingUrl("/loginProc") // �α��� ���μ��� ������ URL ����
                                .permitAll()
                ); // Ŀ���� �α��� �����Ͽ��� Admin �������� ���� ������ ���ٽ� ���Ѿ��� �ߴ� ���� �α��� �������� �̵�
        http
                .csrf((auth) -> auth.disable()); // �׽�Ʈ�� ���� csrf ��� ����

        return http.build();

    }

}
