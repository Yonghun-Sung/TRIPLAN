package com.site.triplan.service;

import com.site.triplan.mapper.AdminMapper;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public List<UserVo> postAllUser() {return adminMapper.findAll();}
    public List<UserVo> postBanUser() { return adminMapper.findBan(); }
    public List<UserVo> postDropUser() { return adminMapper.findDrop(); }
    public List<ReportVo> postUnreport() { return adminMapper.findUnreport(); }

}
