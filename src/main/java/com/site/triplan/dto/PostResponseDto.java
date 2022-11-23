package com.site.triplan.dto;

import com.site.triplan.vo.UserVo;
import lombok.Getter;

@Getter
public class PostResponseDto {
    String id;
    String name;
    String pw;
    String nickname;
    Integer user_code;

    public PostResponseDto(UserVo userVo) {
        this.id = userVo.getId();
        this.name = userVo.getName();
        this.pw = userVo.getPw();
        this.nickname = userVo.getNickname();
        this.user_code = userVo.getUser_code();
    }
}
