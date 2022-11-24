package com.site.triplan.mapper;

import com.site.triplan.vo.LikePlanVo;
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
    List<LikePlanVo> getAllLikePlans();
    //회원정보수정에서 이메일, 이름, 닉네임출력
    UserVo getUserProfile();

}
