package com.site.triplan.mapper;

import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    Integer getPlanCount(String code);

    Integer getReplyCount(String code);//usercode

    Integer getLikeCount(String code);

    /*Integer getPlaceCount(); //좋아요한 여행일정의 장소개수 알려고*/

    //나의 댓글
    List<ReplyVo> getAllReplies(String code);

    //좋아요한 일정
    List<PlanVo> getAllLikePlans(String code);

    //회원정보수정에서 이메일, 이름, 닉네임출력
    UserVo getUserProfile(String id);//session에서 받아올 id

    //나의 일정들
    /*List<PlanVo> getAllMyPlans();*/
    //다가오는 일정
    List<PlanVo> getScheduledPlans(String code);

    List<PlanVo> getCompletedPlans(String code);

/*    //나의 일정 삭제
    void deleteByplanId(Integer id);
    //좋아요한 일정 삭제(좋아요테이블에서인듯?일정자체가 없어지는게 아니라!)
    void deleteBylikeId(Integer id);
    //나의 댓글 삭제
    void deleteByreplyId(Integer id);*/

    //여행 제목 수정
    void updateTitle(String title, Integer code);

    //나의 일정 삭제
    void deletePlans(Integer code);//plan_code

    //체크한 좋아요한 일정 삭제
    void deleteLike(String code, Integer plan_code);
    /*    Integer updatePlanTitle(@Param("plan") PlanVo plan);
    Integer insertToPlanDeleted(@Param("delete_plan") )*/
    //체크한 댓글 삭제
    void deleteReply(String code, Integer reply_code);
    //회원정보 수정(닉네임, 패스워드) 아이디필요
    void updateUser(String nickname, String pw, String id);
    // 회원탈퇴테이블로 옮기기
    /*UserVo userToDropTbl(@Param("id") String id, @Param("name") String name, @Param("nickname") String nickname, @Param("user_code") Integer user_code);*/
    void userToDropTbl(UserVo user);
}