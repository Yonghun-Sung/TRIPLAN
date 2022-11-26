package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanVo {
    String name;
    String title;
    String nickname;
    Integer views;
    String start_dt;
    String end_dt;

    String photo_path; // 지역테이블에 이미지 넣어두고 그거 멤버변수로 하나만들기
    String id; // 작성자 이메일
    String write_dt; // 일정 작성일

    Integer code; //일정 코드-삭제할때필요


    //좋아요한 일정
    public PlanVo(String name, String title, String nickname, Integer views, String start_dt, String end_dt, String photo_path, Integer code) {
        this.name = name;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        this.photo_path = photo_path;
        this.code = code;
    }

    //나의 일정
    public PlanVo(String name, String title, String nickname, Integer views, String start_dt, String end_dt, String photo_path, String id, String write_dt) {
        this.name = name;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        this.photo_path = photo_path;
        this.id = id;
        this.write_dt = write_dt;
    }
}
