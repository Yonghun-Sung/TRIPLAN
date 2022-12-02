package com.site.triplan.vo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class AdminVo implements UserDetails {   // security에서 지정한 형식 = UserDetails
    private String id;
    private String pw;
    private String name;
    private String auth;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {        // 사용자 권한을 콜렉션 형태로 반환
        return Collections.singletonList(new SimpleGrantedAuthority(this.auth));
    }

    @Override
    public String getUsername(){        // sceurity username -> 인증할 때 id
        return this.id;
    }
    @Override
    public  String getPassword(){
        return this.pw;
    }

    public  String getUserName(){
        return  this.name;
    }


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
