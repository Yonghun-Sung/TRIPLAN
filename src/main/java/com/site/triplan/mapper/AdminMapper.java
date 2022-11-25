package com.site.triplan.mapper;


import com.site.triplan.vo.ReportVo;

import ch.qos.logback.core.pattern.PostCompileProcessor;

import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserVo> findAll();     // 전체회원 데이터
    List<UserVo> findBan();     // 정지회원 데이터
    List<UserVo> findDrop();    // 탈퇴회원 데이터

    List<ReportVo> findUnreport();
    List<ReportVo> findReport();

}
