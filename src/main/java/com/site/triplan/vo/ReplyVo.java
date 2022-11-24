package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyVo {
    //댓글.content, reply.write_dt, reply.plan_code, plan.title, plan.area_code, area.name//reply: r plan :p area: a
    String content; //reply.content
    String write_dt; //reply.write_dt
    String title; //plan.title
    String name; //area.name
    //user_code받아야하나?


    public ReplyVo(String content, String write_dt, String title, String name) {
        this.content = content;
        this.write_dt = write_dt;
        this.title = title;
        this.name = name;
    }
}
