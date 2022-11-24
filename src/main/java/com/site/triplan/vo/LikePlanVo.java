package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikePlanVo {
    String name;
    String title;
    String nickname;
    Integer views;
    String start_dt;
    String end_dt;
    /*String photo_path; // 지역테이블에 이미지 넣어두고 그거 멤버변수로 하나만들기*/

    public LikePlanVo(String name, String title, String nickname, Integer views, String start_dt, String end_dt/*, String photo_path*/) {
        this.name = name;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
       /* this.photo_path = photo_path;*/
    }
}
