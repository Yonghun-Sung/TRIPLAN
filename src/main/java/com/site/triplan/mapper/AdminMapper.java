package com.site.triplan.mapper;


import com.site.triplan.vo.ReportVo;

import ch.qos.logback.core.pattern.PostCompileProcessor;

import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserVo> findAll();     // 전체회원 
    List<UserVo> findBan();     // 정지회원 
    List<UserVo> findDrop();    // 탈퇴회원 

    List<ReportVo> findUnreport();  // 미처리신고 
    List<ReportVo> findReport();    // 처리신고


}
