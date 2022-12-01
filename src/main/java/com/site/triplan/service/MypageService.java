package com.site.triplan.service;

import com.site.triplan.mapper.MypageMapper;
import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserDto;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MypageService {
    private MypageMapper mypageMapper;

    public MypageService(MypageMapper mypageMapper) {
        this.mypageMapper = mypageMapper;
    }

    
    public List<ReplyVo> getAllList(String code) { //댓글
        return mypageMapper.getAllReplies(code);
    }

    public Integer getAllReplyCount(String code) {
        return mypageMapper.getReplyCount(code);
    }

    public Integer getAllPlanCount(String code) {
        return mypageMapper.getPlanCount(code);
    }

    public Integer getAllLikeCount(String code) {
        return mypageMapper.getLikeCount(code);
    }

    public List<PlanVo> getAllLikeList(String code) { /*좋아요한 일정들*/
        return mypageMapper.getAllLikePlans(code);
    }

    /*public Integer getPlaceNum() {
        return mypageMapper.getPlaceCount();
    }*/

    public UserVo getMyProfile(String id) {
        return mypageMapper.getUserProfile(id);
    }

    /*public List<PlanVo> getAllPlanList() {
        return mypageMapper.getAllMyPlans();
    }*/

    public List<PlanVo> getScheduledList(String code) {
        List<PlanVo> getScheduledList = mypageMapper.getScheduledPlans(code);

        //return mypageMapper.getScheduledPlans(code);
        return getScheduledList;
    }

    public List<PlanVo> getCompletedList(String code) {

        return mypageMapper.getCompletedPlans(code);
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

//    public boolean updateTitle(PlanVo plan) {
//        Integer result = mypageMapper.updatePlanTitle(plan);
//        return result == 1;
//    }
//
    //나의 일정 삭제
    public void deletePlans(Integer code) {
        mypageMapper.deletePlans(code);
    }

    //좋아요한 일정 삭제
    public void deleteLike(String code, Integer plan_code) {
        mypageMapper.deleteLike(code, plan_code);
    }

    //선택한 댓글 삭제
    public void deleteReply(String code, Integer reply_code) {
        mypageMapper.deleteReply(code, reply_code);
    }

    //회원정보 수정(닉네임, 비밀번호)
    /*public void updateUser(String nickname, String pw, String id) {
        mypageMapper.updateUser(nickname, pw, id);
    }*/
    //회원정보 수정 시 비밀번호 유효성 체크
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error: errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    // 회원정보 수정
    public void updateUser(UserDto userDto) {
        mypageMapper.updateUser(userDto);
    }

    // 탈퇴버튼 -> 회원탈퇴 테이블로 이동
    /*public UserVo userToDropTbl(String id, String name, String nickname, Integer user_code) {
        return mypageMapper.userToDropTbl()
    }*/
    public void userToDropTbl(UserVo user) {
        mypageMapper.userToDropTbl(user);
    }
}
