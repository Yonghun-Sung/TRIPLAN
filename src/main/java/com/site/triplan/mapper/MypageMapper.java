package com.site.triplan.mapper;

import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {

    //다가올 일정
    List<PlanVo> getScheduledPlans(String code);
    //완료된 일정
    List<PlanVo> getCompletedPlans(String code);
    //나의 댓글
    List<ReplyVo> getAllReplies(String code);
    //좋아요한 일정
    List<PlanVo> getAllLikePlans(String code);
    //회원정보
    UserVo getUserProfile(String id);


    // 나의 일정 개수
    Integer getPlanCount(String code);
    // 나의 댓글 개수
    Integer getReplyCount(String code);
    // 좋아요한 일정 개수
    Integer getLikeCount(String code);


    //여행 제목 수정
    void updateTitle(String title, Integer code);
    //나의 일정 삭제
    void deletePlans(Integer code);//plan.code
    //나의 댓글 삭제
    void deleteReply(String code, Integer reply_code);
    //좋아요한 일정 삭제
    void deleteLike(String code, Integer plan_code);


    //회원정보 수정(닉네임, 패스워드), id사용
    void updateUser(String nickname, String pw, String id);
    // 회원탈퇴
    void userToDropTbl(UserVo user);


    //동행자 검색
    UserVo searchMate(String mateEmail);
    //동행자 추가
    void addMate(Integer plan_code, Integer user_code);
}