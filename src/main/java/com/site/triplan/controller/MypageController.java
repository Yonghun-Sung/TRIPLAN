package com.site.triplan.controller;

import com.site.triplan.service.MypageService;
import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/triplan")
public class MypageController {

    private MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }


    @RequestMapping("/mypage/like")
    public String mypagelike(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            /*맨 위 닉네임 한글자 + 닉네임*/
            UserVo userProfile = mypageService.getMyProfile();
            //Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            //model.addAttribute("firstletterNickname", nicknameFirst);

            String nickname = (String)session.getAttribute("session_nickname");
            Character nicknameFirst = nickname.charAt(0);
            model.addAttribute("firstletterNickname",nicknameFirst);



            Integer planCount = mypageService.getAllPlanCount();
            Integer replyCount = mypageService.getAllReplyCount();
            Integer likeCount = mypageService.getAllLikeCount();
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);
            model.addAttribute("likecount", likeCount);
            List<PlanVo> likeList = mypageService.getAllLikeList();
            model.addAttribute("likelist",likeList);

            Integer placeCount = mypageService.getPlaceNum();
            model.addAttribute("placecount", placeCount);
            view = "user_mypage_like";
        }
        return view;
    }
    /*다가올 일정*/
    @RequestMapping("/mypage")
    public String mypage(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile();
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount();
            Integer replyCount = mypageService.getAllReplyCount();
            Integer likeCount = mypageService.getAllLikeCount();
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);
            model.addAttribute("likecount", likeCount);
            Integer placeCount = mypageService.getPlaceNum();
            model.addAttribute("placecount", placeCount);

            /*List<PlanVo> planList = mypageService.getAllPlanList();
            model.addAttribute("planlist", planList);

            view = "user_mypage_main2";*/

            List<PlanVo> schPlanList = mypageService.getScheduledList();
            model.addAttribute("schplanlist",schPlanList);

            view = "user_mypage_scheduledplan";
        }
        return view;
    }

    /*완료된 일정*/
    @RequestMapping("/mypage/completedplans")
    public String mypageCompletedPlans(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile();
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount();
            Integer replyCount = mypageService.getAllReplyCount();
            Integer likeCount = mypageService.getAllLikeCount();
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);
            model.addAttribute("likecount", likeCount);
            Integer placeCount = mypageService.getPlaceNum();
            model.addAttribute("placecount", placeCount);

            List<PlanVo> comPlanList = mypageService.getCompletedList();
            model.addAttribute("complanlist",comPlanList);


            view = "user_mypage_completedplan";
        }
        return view;
    }

    @RequestMapping("/myprofile")
    public String myprofile(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile();
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);//받아온 UserVo에서 닉네임만 string으로 넘김,[0]인덱스 한글자만 보여주려고
            view = "user_profile_edit";
        }
        return view;
    }

    @RequestMapping("/mypage/reply")
    public String mypagereply(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile();
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount();
            Integer replyCount = mypageService.getAllReplyCount();
            Integer likeCount = mypageService.getAllLikeCount();
            List<ReplyVo> replyList = mypageService.getAllList();
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);;
            model.addAttribute("likecount", likeCount);
            model.addAttribute("replies", replyList);
            view = "user_mypage_reply";
        }
        return view;
    }


}
/*----캐쉬 사용함..보통은--------------------------------각뷰페이지마다 같은 거를 모델로 넘겨줘야하는데{{}} 따로 분리를 할까 했더니 url도 같고....---*/
/*그부분만 파일로 따로 뺴고 각 뷰에서그부분 지우고 include{{>}}해준 상태로*/
