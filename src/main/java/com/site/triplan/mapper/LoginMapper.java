package com.site.triplan.mapper;

import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    // 로그인
    UserVo loginUser(UserVo loginInfo);

    // ID 중복 확인
    Integer countId(String id);

    // 비밀번호 변경
    void updatePw(String pw, String id);

    // 탈퇴회원 확인
    Integer checkDropId(String id);

    // 회원가입
    //-- 회원테이블
    void insertUser();
    //-- 최대 회원코드 찾기
    Integer getMaxUserCode();
    //-- 회원정보테이블
    void insertUserInfo(UserVo user);
}
