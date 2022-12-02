package com.site.triplan.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    Integer area_code, user_code;
    // 일정보기
    String area_name, area_engname;

    Integer place_num, reply_num;  //--장소 개수, 댓글 개수

    //좋아요한 일정
    public PlanVo(String name, String title, String nickname, Integer views, String start_dt, String end_dt, String photo_path, Integer code, Integer place_num, String area_engname) {
        this.name = name;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        this.photo_path = photo_path;
        this.code = code;
        this.place_num = place_num;
        this.area_engname = area_engname;
    }
    //나의 일정
    public PlanVo(String name, String title, String nickname, Integer views, String start_dt, String end_dt, String id, String write_dt, Integer code, Integer place_num, Integer user_code, String area_engname) {
        this.name = name;
        this.title = title;
        this.nickname = nickname;
        this.views = views;
        this.start_dt = start_dt;
        this.end_dt = end_dt;
        /*this.photo_path = photo_path;*/
        this.id = id;
        this.write_dt = write_dt;
        this.code = code;
        this.place_num = place_num;
        this.user_code = user_code;
        this.area_engname = area_engname;
    }
    public PlanVo(Integer code, Integer user_code) {
        this.code = code;
        this.user_code = user_code;
    }

}
