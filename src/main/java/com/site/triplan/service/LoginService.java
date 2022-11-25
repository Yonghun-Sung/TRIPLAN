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
    public UserVo loginUser(UserVo loginInfo) {
        UserVo user = loginMapper.loginUser(loginInfo);
        return user;
    }
}
