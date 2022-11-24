package com.site.triplan.mapper;

import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    // 로그인
    UserVo loginUser(UserVo loginInfo);
}
