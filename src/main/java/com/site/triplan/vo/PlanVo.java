package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanVo {
    Integer code, views, area_code, user_code;
    String title, start_dt, end_dt, write_dt;
}
