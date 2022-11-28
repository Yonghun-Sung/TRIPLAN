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

    public List<UserVo> postAllUser() {
        return adminMapper.findAll();}
    public List<UserVo> postBanUser() {
        return adminMapper.findBan(); }
    public List<UserVo> postDropUser() {
        return adminMapper.findDrop(); }
    public List<ReportVo> postUnreport() {
        return adminMapper.findUnreport(); }
    public List<ReportVo> postReport() {
        return adminMapper.findReport(); }



    // 신고 처리
    public void processReport(ReportVo reportVo) {
        reportVo.setAdmin_code(1);
        adminMapper.processReport(reportVo);
    }

    public void processedReport(ReportVo reportVo){
        System.out.println(reportVo.getReg_code());
        System.out.println(reportVo.getResult_code());
//        adminMapper.processedReport(reportVo);
    }
}
