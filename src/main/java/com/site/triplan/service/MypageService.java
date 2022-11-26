package com.site.triplan.service;

import com.site.triplan.mapper.MypageMapper;
import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MypageService {
    private MypageMapper mypageMapper;

    public MypageService(MypageMapper mypageMapper) {
        this.mypageMapper = mypageMapper;
    }

    
    public List<ReplyVo> getAllList() { //댓글
        return mypageMapper.getAllReplies();
    }

    public Integer getAllReplyCount() {
        return mypageMapper.getReplyCount();
    }

    public Integer getAllPlanCount() {
        return mypageMapper.getPlanCount();
    }

    public Integer getAllLikeCount() {
        return mypageMapper.getLikeCount();
    }

    public List<PlanVo> getAllLikeList() { /*좋아요한 일정들*/
        return mypageMapper.getAllLikePlans();
    }

    public Integer getPlaceNum() {
        return mypageMapper.getPlaceCount();
    }

    public UserVo getMyProfile() {
        return mypageMapper.getUserProfile();
    }

    /*public List<PlanVo> getAllPlanList() {
        return mypageMapper.getAllMyPlans();
    }*/

    public List<PlanVo> getScheduledList() {
        return mypageMapper.getScheduledPlans();
    }

    public List<PlanVo> getCompletedList() {
        return mypageMapper.getCompletedPlans();
    }

/*    //나의 댓글 삭제
    public Integer deleteReply(final Integer id) {
        mypageMapper.deleteByreplyId(id);
        return id;
    }

    //나의 일정 삭제
    public Integer deleteMyPlans(final Integer id) {
        mypageMapper.deleteByplanId(id);
        return id;
    }

    //좋아요 일정 삭제<-좋아요한 일정이 사라지는게 아니라 내mypage에서 사라짐 +좋아요테이블
    public Integer deleteLike(final Integer id) {
        mypageMapper.deleteBylikeId(id);
        return id;
    }*/
    //여행제목 업데이트



    //일정 수정된 화면 보여지기

    //댓글 삭제되면->화면 업데이트

    //회원정보 수정~

    //여행제목수정
    public void updateTitle(String title,Integer code){
        mypageMapper.updateTitle(title, code);
    }

}
