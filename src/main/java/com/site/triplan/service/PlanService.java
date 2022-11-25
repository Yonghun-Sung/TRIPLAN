package com.site.triplan.service;

import com.site.triplan.mapper.PlanMapper;
import com.site.triplan.vo.AreaVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {
    private PlanMapper planMapper;

    public PlanService(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }

    // 지역
    public List<AreaVo> getAreaInfo() {
        List<AreaVo> areaList = new ArrayList<>();
        areaList = planMapper.getAreaInfo();
        return areaList;
    }
}
