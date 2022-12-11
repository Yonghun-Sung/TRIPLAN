package com.site.triplan.security;

import com.site.triplan.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity                  // security 활성화
@RequiredArgsConstructor            // 생성자 자동생성 및 final 변수 의존관계 자동 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {      //  security에서 지정한 형식 - WebSecurityConfigurerAdapter

    private final AdminService adminService;

    @Override
    protected void configure(HttpSecurity http) throws  Exception {
        http
                .csrf().disable();          // 사이트 간 요청 위조 공격 방지 기능 켜기
        http
                .formLogin()
                .loginPage("/triplan/adminLogin")                                  // custom 로그인 페이지
                .loginProcessingUrl("/triplan/login_proc")                         // 로그인 폼 액션 url
                .defaultSuccessUrl("/triplan/admin")                               // 로그인 성공 후 이동
                .failureUrl("/triplan/adminLogin?errCode=1");
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/triplan/adminLogin").permitAll()         // adminLogin 접속 허용
                .antMatchers("/triplan/admin").hasRole("ADMIN")         // admin 접속 권한, DB에 넣을때는 ROLE_권한 형식으로
                .antMatchers("/triplan/admin/**").hasRole("ADMIN")      // admin/@ 접속 권한
                .anyRequest().permitAll();
        http
                .logout()
                .logoutUrl("/triplan/adminLogout")
                .logoutSuccessUrl("/triplan/adminLogin");      // 로그아웃시 이동
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws  Exception{
        auth
                .userDetailsService(adminService).passwordEncoder(new BCryptPasswordEncoder());

    }
}
