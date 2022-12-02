package com.site.triplan.service;

import com.site.triplan.mapper.PlanMapper;
import com.site.triplan.vo.*;
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

    // 일정 리스트 (지역코드 사용)
    public List<PlanVo> getPlanList(String area_code) {
        List<PlanVo> planList = new ArrayList<>();
        planList = planMapper.getPlanList(area_code);
        return planList;
    }

    // 지역
    public List<AreaVo> getAreaInfo() {
        List<AreaVo> areaList = new ArrayList<>();
        areaList = planMapper.getAreaInfo();
        return areaList;
    }

    // 일정 작성
    //-- id로 회원코드 찾기
    public int getUserCodeById(String id) {
        int user_code = planMapper.getUserCodeById(id);
        return user_code;
    }
    @Transactional
    public String insertPlan(PlanVo plan, List<AttractionVo> placeList) {
        int result = planMapper.insertPlan(plan);
        String plan_code = "";
        if (result > 0) {
            plan_code = planMapper.getMaxPlanCode();
            for (int i = 0; i < placeList.size(); i++) {
                placeList.get(i).setPlan_code(plan_code);
                result = planMapper.insertPlace(placeList.get(i));
                if (result < 0) {
                    return null;
                }
            }
        } else {
            return null;
        }
        return plan_code;
    }

    // 일정 상세보기
    //-- 일정 내용
    public PlanVo getPlanDetail(String plan_code) {
        PlanVo plan = planMapper.getPlanDetail(plan_code);
        return plan;
    }
    //-- 조회수 업데이트
    public int updateViews(String plan_code) {
        int result = planMapper.updateViews(plan_code);
        return result;
    }
    //-- 좋아요 여부
    public int isLike(String plan_code, Integer user_code) {
        int isLike = planMapper.isLike(plan_code, user_code);
        return isLike;
    }
    //-- 좋아요 등록
    public int insertLike(String plan_code, Integer user_code) {
        int result = planMapper.insertLike(plan_code, user_code);
        return result;
    }
    //-- 좋아요 해제
    public int deleteLike(String plan_code, Integer user_code) {
        int result = planMapper.deleteLike(plan_code, user_code);
        return result;
    }
    //-- 장소 목록
    public List<AttractionVo> getPlaceList(String plan_code) {
        List<AttractionVo> placeList = new ArrayList<>();
        placeList = planMapper.getPlaceList(plan_code);
        return placeList;
    }
    //-- 댓글 목록
    public List<ReplyVo> getReplyList(String plan_code) {
        List<ReplyVo> replyList = new ArrayList<>();
        replyList = planMapper.getReplyList(plan_code);
        return replyList;
    }
    //-- 댓글 등록
    public int insertReply(ReplyVo reply) {
        int result = planMapper.insertReply(reply);
        return result;
    }
    //-- 댓글 수정
    public int updateReply(ReplyVo reply) {
        int result = planMapper.updateReply(reply);
        return result;
    }
    //-- 댓글 삭제
    public int deleteReply(ReplyVo reply) {
        int result = planMapper.deleteReply(reply);
        return result;
    }
    //-- 동행자 목록
    public List<MateVo> getMateList(String plan_code) {
        List<MateVo> mateList = planMapper.getMateList(plan_code);
        return mateList;
    }
}
