package org.example.spring_security_ex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
                "ROLE_USER > ROLE_GUEST");

        return hierarchy;
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
                                .requestMatchers("/", "/login","/loginProc", "/join", "joinProc").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                );

        //login form ���
        http
                .formLogin(
                        (auth) -> auth
                                .loginPage("/login")
                                .loginProcessingUrl("/loginProc") // �α��� ���μ��� ������ URL ����
                                .permitAll()
                ); // Ŀ���� �α��� �����Ͽ��� Admin �������� ���� ������ ���ٽ� ���Ѿ��� �ߴ� ���� �α��� �������� �̵�

        /*
        //http basic �������
        http
                .httpBasic(Customizer.withDefaults());
        */

        //CSRF : ��û�� �����Ͽ� ����ڰ� ��ġ �ʾƵ� ���������� ������ ��û�� ������ ���
        //disable ���������� �⺻���� enable
        //enable �ϱ� ���ؼ��� Csrf Token �� �����ؾ��Ѵ�.
        http.csrf((auth) -> auth.disable()); // �׽�Ʈ�� ���� csrf ��� ����


        // ���� �α��� ����
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // ���� �α��� ��� ����
                        .maxSessionsPreventsLogin(true) // ���� �α��� ������ �ʰ��Ͽ��� ��� ó�� ���
                        //true : �ʰ��� �α��� ���� , flase : �ʰ��� ���� ���� �ϳ� ����
                );

        http
                .sessionManagement((auth) -> auth
//                        .sessionFixation().none() // �α��� �� ���� ���� �������
//                        .sessionFixation().newSession() // �α��� �� ���� ���� ����
                        .sessionFixation().changeSessionId() // �α��ν� ������ ���ǿ� ���� ID ����
                );

        //�α׾ƿ� ����
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));

        return http.build();

    }

    //InMemory ������� Ư�� ������ �����Ͽ� ����
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user1 = User.builder()
                .username("user1")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("user2")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

}
