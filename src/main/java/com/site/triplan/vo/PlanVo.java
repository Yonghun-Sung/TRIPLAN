package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanVo {
<<<<<<< HEAD
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

    Integer area_code, user_code;
    // 일정보기
    String area_name, area_engname;

    Integer place_num, reply_num;  //--장소 개수, 댓글 개수

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
    public PlanVo(String name, String title, String nickname, Integer views, String start_dt, String end_dt, String photo_path, String id, String write_dt, Integer code) {
        this.name = name;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        this.photo_path = photo_path;
        this.id = id;
        this.write_dt = write_dt;
        this.code = code;
    }
    /*public PlanVo(String title, Integer code) {
        this.code = code;
        this.title = title;
    }*/

}
