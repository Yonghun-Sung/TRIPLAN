package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanVo {
    Integer code, views, area_code, user_code;
    String title, start_dt, end_dt, write_dt;
    // 일정보기
    String area_name, area_engname, nickname;
    Integer place_num;  //--장소 개수
}
