package com.shop.config;

import com.shop.serivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity          // SpringSecurityFilterChain이 자동으로 포함됨.
//SpringBoot 에서는 @EnableJpaRepositories가 자동설정이 돼서 생략해도 됨.
public class SecurityConfig {

    @Autowired
    MemberService memberservice;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .authorizeRequests()
////                .antMatchers("/members/login").authenticated()     // /members/**은 인증된 사용자에게만
//                //.anyRequest(): 설정된 값 외에 나머지 URL
//                .anyRequest().permitAll()   //나머지 URL은 전부 허용(로그인한 사용자만)
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/","/members/**","/item/**","/images").permitAll()    //인증없이 로그인 가능
                .antMatchers("/admin/**").hasRole("ADMIN")  //ADMIN Role일 경우에만 접근 가능
                .anyRequest().permitAll()   //나머지 경로들은 인증 후에 로그인 가능

                .and()

                .formLogin()        //Form Login은 아이디와 비밀번호를 입력해서 들어오는 로그인 형태를 지원하는 Spring Security 기능
                .loginPage("/members/login")    //login 페이지 url 설정
                .defaultSuccessUrl("/")         //로그인 성공시 이동할 URL
                .usernameParameter("email")     //로그인 성공시 사용할 파라미터 이름으로 email을 지정
                .failureUrl("/members/login/error")     //로그인 실패시 이동할 URL
                .and()      //form login 종료 지점
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))     //로그아웃 URL설정
                .logoutSuccessUrl("/");     //로그아웃 성공시 이동할 url

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {  //BCryptPasswordEncoder 해시함수를 이용하여 비밀번호를 암호화하여 저장
        //BCryptPasswordEncoder를 빈으로 등록하여 사용
        return new BCryptPasswordEncoder();
    }

}




