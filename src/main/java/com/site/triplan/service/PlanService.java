package com.site.triplan.service;

import com.site.triplan.mapper.PlanMapper;
import com.site.triplan.vo.AreaVo;
import com.site.triplan.vo.AttractionVo;
import com.site.triplan.vo.PlanVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 일정 작성
    @Transactional
    public Integer insertPlan(PlanVo plan, AttractionVo place) {
        return 1;
    }
}
