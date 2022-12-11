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

    // 다가올 일정 목록
    public List<PlanVo> getScheduledList(String code) {
        return mypageMapper.getScheduledPlans(code);
    }
    // 완료된 일정 목록
    public List<PlanVo> getCompletedList(String code) {
        return mypageMapper.getCompletedPlans(code);
    }
    // 나의 댓글 목록
    public List<ReplyVo> getAllList(String code) { //댓글
        return mypageMapper.getAllReplies(code);
    }
    // 좋아요한 일정 목록
    public List<PlanVo> getAllLikeList(String code) { /*좋아요한 일정들*/
        return mypageMapper.getAllLikePlans(code);
    }
    // 회원 정보
    public UserVo getMyProfile(String id) {
        return mypageMapper.getUserProfile(id);
    }

    // 나의 일정 개수
    public Integer getAllPlanCount(String code) {
        return mypageMapper.getPlanCount(code);
    }
    // 나의 댓글 개수
    public Integer getAllReplyCount(String code) {
        return mypageMapper.getReplyCount(code);
    }
    // 좋아요한 일정 개수
    public Integer getAllLikeCount(String code) {
        return mypageMapper.getLikeCount(code);
    }

    // 여행 제목 수정
    public void updateTitle(String title,Integer code){
        mypageMapper.updateTitle(title, code);
    }
    // 나의 일정 삭제
    public void deletePlans(Integer code) {
        mypageMapper.deletePlans(code);
    }
    // 나의 댓글 삭제
    public void deleteReply(String code, Integer reply_code) {
        mypageMapper.deleteReply(code, reply_code);
    }
    // 좋아요한 일정 삭제
    public void deleteLike(String code, Integer plan_code) {
        mypageMapper.deleteLike(code, plan_code);
    }

    //회원정보 수정(닉네임, 비밀번호)
    public void updateUser(String nickname, String pw, String id) {
        mypageMapper.updateUser(nickname, pw, id);
    }
    // 회원 탈퇴 -> drop테이블에 추가
    public void userToDropTbl(UserVo user) {
        mypageMapper.userToDropTbl(user);
    }

    // 동행자 검색
    public UserVo searchMate(String mateEmail) {
        return mypageMapper.searchMate(mateEmail);
    }
    // 동행자 추가
    public void addMate(Integer plan_code, Integer user_code) {
        mypageMapper.addMate(plan_code, user_code);
    };
}
