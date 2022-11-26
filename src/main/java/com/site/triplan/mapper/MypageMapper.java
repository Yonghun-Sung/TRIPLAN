package com.site.triplan.mapper;

import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    Integer getPlanCount();
    Integer getReplyCount();
    Integer getLikeCount();
    Integer getPlaceCount(); //좋아요한 여행일정의 장소개수 알려고

    //나의 댓글
    List<ReplyVo> getAllReplies();
    //좋아요한 일정
    List<PlanVo> getAllLikePlans();
    //회원정보수정에서 이메일, 이름, 닉네임출력
    UserVo getUserProfile();
    //나의 일정들
    /*List<PlanVo> getAllMyPlans();*/
    //다가오는 일정
    List<PlanVo> getScheduledPlans();

    List<PlanVo> getCompletedPlans();

/*    //나의 일정 삭제
    void deleteByplanId(Integer id);
    //좋아요한 일정 삭제(좋아요테이블에서인듯?일정자체가 없어지는게 아니라!)
    void deleteBylikeId(Integer id);
    //나의 댓글 삭제
    void deleteByreplyId(Integer id);*/

    void updateTitle(String title, Integer code);
}
