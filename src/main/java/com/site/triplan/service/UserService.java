package com.site.triplan.service;

import com.site.triplan.mapper.AdminMapper;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private AdminMapper adminMapper;

    public UserService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public List<UserVo> getAllUser() {
        return adminMapper.findAll();
    }
}
