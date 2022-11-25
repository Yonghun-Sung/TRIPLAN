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
}
