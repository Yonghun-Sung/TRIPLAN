package com.site.triplan.mapper;

import com.site.triplan.vo.AreaVo;
import com.site.triplan.vo.AttractionVo;
import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    // 일정보기
    List<PlanVo> getPlanList(String area_code);

    // 일정 상세보기
    //-- 일정 내용
    PlanVo getPlanDetail(String plan_code);
    //-- 찜 여부
    Integer isLike(String plan_code, Integer user_code);
    //-- 장소 목록
    List<AttractionVo> getPlaceList(String plan_code);
    //-- 댓글 목록
    List<ReplyVo> getReplyList(String plan_code);

    // 지역
    List<AreaVo> getAreaInfo();

    // 일정 작성
    //-- id로 회원코드 찾기
    Integer getUserCodeById(String id);

    //-- 일정 INSERT
    void insertPlan(PlanVo plan);

    //-- 최대 일정코드 찾기
    Integer getMaxPlanCode();

    //-- 장소 INSERT
    void insertPlace(AttractionVo place);
}
