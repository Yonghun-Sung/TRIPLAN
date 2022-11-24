package com.site.triplan.mapper;

import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    // 로그인
    String loginUser(UserVo user);

    // 닉네임 가져오기(아이디 사용): header
    String getNickname(String id);
}
