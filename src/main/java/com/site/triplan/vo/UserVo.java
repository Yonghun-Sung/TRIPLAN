package com.site.triplan.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    String id;
    String pw;
    String name;
    String nickname;
    String reg_dt;
    String dt;
    String state;
    Integer cnt;
    Integer user_code;

    String date;

    // 회원 탈퇴 시  drop테이블에 넣을 정보 객체
    // 동행자 추가 회원 불러올 정보
    public UserVo(String id, String name, String nickname, Integer user_code) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.user_code = user_code;
    }

}
