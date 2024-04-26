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
     * 로그인시 비밀번호에 대해 단방향 해시 암호화를 진행하여 저장되어 있는 비밀번호와 대조한다.
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(); // 생성자를 리턴하여 자동으로 생성되도록 한다.
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * 동작은 상단부 부터 차례대로 시작되기 때문에 순서 설정에 유의한다. 
         * .permitAll() 모두에게 
         * .hasRole() 권한 단수  .hasAnyRole() 권한 복수
         * .authenticated() 로그인 된 유저만
         * 2.x ~ 2.6.x  WebSecurityCofigurerAdapter 상속 받아  Configuer 오버라이드 하여 사용
         * 2.7 : SecurityFilterChain InterFace 를 Bean 으로 등록하여 사용
         * 3.1  부터는 람다를 사용해야한다.
         *      자동으로 csrf 등록되어있다.
         *      로그인시 비밀번호에 대해 단방향 해시 암호화를 진행하여 저장되어 있는 비밀번호와 대조한다.
         *      BCrypt Password Encoder 를 제공하고  이를 @Bean 으로 등록하여 사용
         *      양방향 : 대칭키 , 비대칭키
         *      단방향 : 해시
         */
        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/", "/login","/loginProc", "/join", "joinProc").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                );
        http
                .formLogin(
                        (auth) -> auth
                                .loginPage("/login")
                                .loginProcessingUrl("/loginProc") // 로그인 프로세싱 진행할 URL 전송
                                .permitAll()
                ); // 커스텀 로그인 진행하여서 Admin 페이지와 같은 페이지 접근시 권한없음 뜨던 것이 로그인 페이지롱 이동

        //CSRF : 요청을 위조하여 사용자가 원치 않아도 서버측으로 강제로 요청을 보내는 방식
        //disable 하지않으면 기본값은 enable
        //enable 하기 위해서는 Csrf Token 을 관리해야한다.
//        http.csrf((auth) -> auth.disable()); // 테스트를 위해 csrf 기능 끄기


        // 다중 로그인 설정
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 다중 로그인 허용 범위
                        .maxSessionsPreventsLogin(true) // 다중 로그인 개수를 초과하였을 경우 처리 방법
                        //true : 초과시 로그인 차단 , flase : 초과시 기존 세션 하나 삭제
                );

        http
                .sessionManagement((auth) -> auth
//                        .sessionFixation().none() // 로그인 시 세션 정보 변경안함
//                        .sessionFixation().newSession() // 로그인 시 세션 새로 생성
                        .sessionFixation().changeSessionId() // 로그인시 동일한 세션에 대한 ID 변경
                );

        //로그아웃 진행
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();

    }


}
