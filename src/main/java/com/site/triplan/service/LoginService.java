package com.site.triplan.service;

import com.site.triplan.mapper.LoginMapper;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private LoginMapper loginMapper;

    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    // 로그인
    public String loginUser(UserVo user) {
        String id = loginMapper.loginUser(user);
        return id;
    }

    // 닉네임 가져오기(아이디 사용): header
    public String getNickname(String id) {
        String nickname = loginMapper.getNickname(id);
        return nickname;
    }
}
