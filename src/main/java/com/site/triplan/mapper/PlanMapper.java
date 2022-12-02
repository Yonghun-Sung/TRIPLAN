package com.site.triplan.mapper;

import com.site.triplan.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    // 일정보기
    List<PlanVo> getPlanList(String area_code);

    // 지역
    List<AreaVo> getAreaInfo();

    // 일정 작성
    //-- id로 회원코드 찾기
    Integer getUserCodeById(String id);

    //-- 일정 INSERT
    Integer insertPlan(PlanVo plan);

    //-- 최대 일정코드 찾기
    String getMaxPlanCode();

    //-- 장소 INSERT
    Integer insertPlace(AttractionVo place);

    // 일정 상세보기
    //-- 일정 내용
    PlanVo getPlanDetail(String plan_code);
    //-- 조회수 업데이트
    Integer updateViews(String plan_code);
    //-- 좋아요 여부
    Integer isLike(String plan_code, Integer user_code);
    //-- 좋아요 등록
    Integer insertLike(String plan_code, Integer user_code);
    //-- 좋아요 해제
    Integer deleteLike(String plan_code, Integer user_code);
    //-- 장소 목록
    List<AttractionVo> getPlaceList(String plan_code);
    //-- 댓글 목록
    List<ReplyVo> getReplyList(String plan_code);
    //-- 댓글 등록
    Integer insertReply(ReplyVo reply);
    //-- 댓글 수정
    Integer updateReply(ReplyVo reply);
    //-- 댓글 삭제
    Integer deleteReply(ReplyVo reply);
    //-- 동행자 목록
    List<MateVo> getMateList(String plan_code);
}
