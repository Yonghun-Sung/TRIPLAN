package com.site.triplan.controller;

import com.site.triplan.service.MypageService;
import com.site.triplan.vo.PlanVo;
import com.site.triplan.vo.ReplyVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private List<String> checkBoxArr; // 댓글, 좋아요한 일정 삭제할 때 사용하는 변수

    // 나의 일정 - 다가올 일정
    @GetMapping("/mypage")
    public String mypage(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");
        String code = String.valueOf(session.getAttribute("session_code"));

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile(id);
            Character nicknameFirst = userProfile.getNickname().charAt(0); // 닉네임 첫글자
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount(code);
            Integer replyCount = mypageService.getAllReplyCount(code);
            Integer likeCount = mypageService.getAllLikeCount(code);
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);
            model.addAttribute("likecount", likeCount);

            List<PlanVo> schPlanList = mypageService.getScheduledList(code);
            model.addAttribute("schplanlist",schPlanList);

            view = "user_mypage_scheduledplan";
        }
        return view;
    }

    // 나의 일정 - 완료된 일정
    @GetMapping("/mypage/completedplans")
    public String mypageCompletedPlans(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");
        String code = String.valueOf(session.getAttribute("session_code"));

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile(id);
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount(code);
            Integer replyCount = mypageService.getAllReplyCount(code);
            Integer likeCount = mypageService.getAllLikeCount(code);
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);
            model.addAttribute("likecount", likeCount);

            List<PlanVo> comPlanList = mypageService.getCompletedList(code);
            model.addAttribute("complanlist",comPlanList);

            view = "user_mypage_completedplan";
        }
        return view;
    }

    // 나의 댓글
    @GetMapping("/mypage/reply")
    public String mypageReply(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");
        String code = String.valueOf(session.getAttribute("session_code"));

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile(id);
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount(code);
            Integer replyCount = mypageService.getAllReplyCount(code);
            Integer likeCount = mypageService.getAllLikeCount(code);
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);;
            model.addAttribute("likecount", likeCount);

            List<ReplyVo> replyList = mypageService.getAllList(code);
            model.addAttribute("replies", replyList);

            view = "user_mypage_reply";
        }
        return view;
    }

    // 좋아요한 일정
    @GetMapping("/mypage/like")
    public String mypageLike(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");
        String code = String.valueOf(session.getAttribute("session_code"));

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile(id);
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);

            Integer planCount = mypageService.getAllPlanCount(code);
            Integer replyCount = mypageService.getAllReplyCount(code);
            Integer likeCount = mypageService.getAllLikeCount(code);
            model.addAttribute("plancount", planCount);
            model.addAttribute("replycount", replyCount);
            model.addAttribute("likecount", likeCount);

            List<PlanVo> likeList = mypageService.getAllLikeList(code);
            model.addAttribute("likelist",likeList);

            view = "user_mypage_like";
        }
        return view;
    }

    //프로필 수정
    @GetMapping("/myprofile")
    public String myprofile(Model model, HttpServletRequest request) {
        String view = "";
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");

        if (id == null) {
            model.addAttribute("errCode", "2");
            view = "redirect:/triplan/loginform?errCode=2";
        } else {
            UserVo userProfile = mypageService.getMyProfile(id);
            Character nicknameFirst = userProfile.getNickname().charAt(0);
            model.addAttribute("userprofile", userProfile);
            model.addAttribute("firstletterNickname", nicknameFirst);
            
            view="user_mypage_update";
        }
        return view;
    }

    //여행제목 수정
    @PutMapping("/mypage/update")
    @ResponseBody
    public Integer updateTitle(@RequestParam String title, @RequestParam Integer code) {
        mypageService.updateTitle(title, code);
        return 1;
    }

    //나의 일정 삭제
    @PostMapping("/mypage/delete")
    public String deletePlans(@RequestParam Integer code, @RequestParam String urlpathname){
        mypageService.deletePlans(code); // plan_deleted 테이블에 insert
        return "redirect:" + urlpathname;
    }

    // 나의 댓글 삭제
    @PostMapping("/mypage/reply/delete")
    public String deleteReply(HttpServletRequest request, @RequestParam(value = "checkBoxArr[]") List<Integer> checkBoxArr) {
        HttpSession session = request.getSession();
        String code = String.valueOf(session.getAttribute("session_code"));

        for(Integer reply_code : checkBoxArr){
            mypageService.deleteReply(code, reply_code);
        }
        return "redirect:/triplan/mypage/reply";
    }

    //좋아요한 일정 삭제
    @PostMapping("/mypage/like/delete")
    public String deleteLike(HttpServletRequest request, @RequestParam(value = "checkBoxArr[]") List<Integer> checkBoxArr) {
        HttpSession session = request.getSession();
        String code = String.valueOf(session.getAttribute("session_code"));

        for(Integer plan_code : checkBoxArr){
            mypageService.deleteLike(code, plan_code);
        }
        return "redirect:/triplan/mypage/like";
    }

    // 회원정보 수정 처리
    @PutMapping("/myprofile/update")
    @ResponseBody
    public String updateMyprofile(HttpServletRequest request, @RequestParam String nickname, @RequestParam String pw) {
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");
        mypageService.updateUser(nickname, pw, id);
        session.setAttribute("session_nickname", nickname);   // 세션에 새로 전달받은 nickname 저장
        return "redirect:/triplan/myprofile";
    }

    // 회원 탈퇴
    @PostMapping("/myprofile/drop")
    public String dropUser(HttpServletRequest request, @RequestBody UserVo uservo) {
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_id");
        String nickname = (String)session.getAttribute("session_nickname");
        String name = uservo.getName();
        Integer user_code = uservo.getUser_code();

        UserVo dropuser = new UserVo(id, name, nickname, user_code);
        mypageService.userToDropTbl(dropuser);
        session.invalidate();
        return "redirect:/triplan/main";
    }

    // 동행자 검색
    @PostMapping("/mypage/searchMate")
    @ResponseBody
    public UserVo searchMate(@RequestParam String mateEmail) {
        return mypageService.searchMate(mateEmail); // 클라이언트로 검색된 동행자 회원 데이터 객체 전달
    }

    // 동행자 추가
    @PostMapping("/mypage/addMate")
    public String addMate(HttpServletRequest request, @RequestBody PlanVo planvo) {
        Integer plan_code = planvo.getCode();
        Integer user_code = planvo.getUser_code();
        mypageService.addMate(plan_code, user_code);
        return "redirect:/triplan/mypage";
    }
}