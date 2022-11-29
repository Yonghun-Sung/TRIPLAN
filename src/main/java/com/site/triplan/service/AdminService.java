package com.site.triplan.service;

import com.site.triplan.mapper.AdminMapper;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {

        this.adminMapper = adminMapper;
    }

    public List<UserVo> postAllUser() {         // 전체회원
        return adminMapper.findAll();}
    public List<UserVo> postBanUser() {         // 정지회원
        return adminMapper.findBan(); }
    public List<UserVo> postDropUser() {        // 탈퇴회원
        return adminMapper.findDrop(); }
    public List<ReportVo> postUnreport() {      // 미처리신고
        return adminMapper.findUnreport(); }
    public List<ReportVo> postReport() {        // 처리내역
        return adminMapper.findReport(); }




    public void processReport(ReportVo reportVo) {     // 미처리신고 신고(승인, 반려)
        reportVo.setAdmin_code(1);
        adminMapper.processReport(reportVo);
    }

    public void processedReport(ReportVo reportVo){     // 처리내역 (철회, 유지)
        adminMapper.processedReport(reportVo);
    }

    public List<UserVo> weeklyNewbie(){
        return adminMapper.weeklyNewbie();
    }

    public List<UserVo> montlyNewbie(){
        return adminMapper.monthlyNewbie();
    }

}
