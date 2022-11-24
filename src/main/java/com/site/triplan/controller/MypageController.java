package com.site.triplan.controller;

import com.site.triplan.service.MypageService;
import com.site.triplan.vo.LikePlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/triplan")
public class MypageController {

    private MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }


    @RequestMapping("/mypage/like")
    public String mypagelike(Model model) {
        Integer planCount = mypageService.getAllPlanCount();
        Integer replyCount = mypageService.getAllReplyCount();
        Integer likeCount = mypageService.getAllLikeCount();
        model.addAttribute("plancount", planCount);
        model.addAttribute("replycount", replyCount);
        model.addAttribute("likecount", likeCount);
        List<LikePlanVo> likeList = mypageService.getAllLikeList();
        model.addAttribute("likelist",likeList);

        Integer placeCount = mypageService.getPlaceNum();
        model.addAttribute("placecount", placeCount);
        return "user_mypage_like";
    }

    @RequestMapping("/mypage/")
    public String mypage(Model model) {
        Integer planCount = mypageService.getAllPlanCount();
        Integer replyCount = mypageService.getAllReplyCount();
        Integer likeCount = mypageService.getAllLikeCount();
        model.addAttribute("plancount", planCount);
        model.addAttribute("replycount", replyCount);

        model.addAttribute("likecount", likeCount);
/*        //planlist들어가기*/

        return "user_mypage_main";
    }

    @RequestMapping("/myprofile")
    public String myprofile(Model model) {

        UserVo userProfile = mypageService.getMyProfile();
        model.addAttribute("userprofile", userProfile);
        return "user_profile_edit";
    }

    @RequestMapping("/mypage/reply")
    public String mypagereply(Model model) {
        Integer planCount = mypageService.getAllPlanCount();
        Integer replyCount = mypageService.getAllReplyCount();
        Integer likeCount = mypageService.getAllLikeCount();
        List<ReplyVo> replyList = mypageService.getAllList();
        model.addAttribute("plancount", planCount);
        model.addAttribute("replycount", replyCount);;
        model.addAttribute("likecount", likeCount);
        model.addAttribute("replies", replyList);
        return "user_mypage_reply";
    }


}
/*----캐쉬 사용함..보통은--------------------------------각뷰페이지마다 같은 거를 모델로 넘겨줘야하는데{{}} 따로 분리를 할까 했더니 url도 같고....---*/
/*그부분만 파일로 따로 뺴고 각 뷰에서그부분 지우고 include{{>}}해준 상태로*/
