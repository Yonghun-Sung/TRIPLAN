package com.site.triplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected  void configure(HttpSecurity http) throws  Exception {
        http
                .csrf().disable();
        http
                .formLogin()
                .loginPage("/triplan/adminLogin")                                  // custom 로그인 페이지
                .loginProcessingUrl("/triplan/login_proc")                         // 로그인 폼 액션 url
                .defaultSuccessUrl("/triplan/admin");                              // 로그인 성공 후 이동
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/triplan/adminLogin").permitAll()         // adminLogin 접속 허용
                .antMatchers("/triplan/admin").hasRole("ADMIN")         // admin 접속 막음
                .antMatchers("/triplan/admin/**").hasRole("ADMIN")      // admin/@ 접속 막음
                .anyRequest().permitAll();
        http
                .logout()
                .logoutUrl("/triplan/adminLogout")
                .logoutSuccessUrl("/triplan/adminLogin");      // 로그아웃시 이동
    }

    @Autowired
    public void authenticate(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("admin@ad.co.kr").
                password("{noop}admin123").
                roles("ADMIN");

    }
}
