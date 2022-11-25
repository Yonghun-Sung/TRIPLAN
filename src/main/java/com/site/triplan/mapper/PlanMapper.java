package com.site.triplan.mapper;

import com.site.triplan.vo.AreaVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    // 지역
    List<AreaVo> getAreaInfo();
}
