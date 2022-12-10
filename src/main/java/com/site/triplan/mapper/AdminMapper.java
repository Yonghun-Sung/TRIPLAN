package com.site.triplan.mapper;


import com.site.triplan.vo.AdminVo;
import com.site.triplan.vo.ReportVo;

import ch.qos.logback.core.pattern.PostCompileProcessor;

import com.site.triplan.vo.UserVo;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserVo> findAll();     // 전체회원 
    List<UserVo> findBan();     // 정지회원 
    List<UserVo> findDrop();    // 탈퇴회원 

    List<ReportVo> findUnreport();  // 미처리신고
    List<ReportVo> findReport();    // 처리내역

    void processReport(ReportVo reportVo);   // 미처리신고 신고(승인, 반려)
    void processedReport(ReportVo reportVo); // // 처리내역 (철회, 유지)

    List<UserVo> weeklyNewbie();    // 주간 신규 회원
    List<UserVo> monthlyNewbie();   // 월간 신규 회원



    AdminVo getAdminAccount(String id);     // security 로그인
    void updatePw(AdminVo adminVo); // 비밀번호 변경
}
