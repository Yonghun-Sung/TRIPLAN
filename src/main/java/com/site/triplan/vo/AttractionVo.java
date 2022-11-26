package com.site.triplan.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttractionVo {
    private String name, addr, imgPath, loc_x, loc_y;
    private String code, memo, photo_path, plan_code;
    private Integer day, order;
    private List<AttractionVo> attractionList;
}
