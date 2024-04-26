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
         * 동작은 상단부 부터 차례대로 시작되기 때문에 순서 설정에 유의한다. 
         * .permitAll() 모두에게 
         * .hasRole() 권한 단수  .hasAnyRole() 권한 복수
         * .authenticated() 로그인 된 유저만
         * 2.x ~ 2.6.x  WebSecurityCofigurerAdapter 상속 받아  Configuer 오버라이드 하여 사용 
         * 2.7 : SecurityFilterChain InterFace 를 Bean 으로 등록하여 사용
         * 부트 3.1 부터는 람다를 사용해야한다.
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
