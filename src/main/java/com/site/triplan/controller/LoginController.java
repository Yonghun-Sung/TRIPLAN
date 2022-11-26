package com.site.triplan.controller;

import com.site.triplan.service.LoginService;
import com.site.triplan.vo.AreaVo;
import com.site.triplan.vo.MailVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/triplan")
public class LoginController {
    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // 로그인폼
    @GetMapping("/loginform")
    public String loginUser() {
        return "user_loginform";
    }

    // 로그인
    @PostMapping("/loginform")
    public String loginUser(Model model, HttpServletRequest request, UserVo loginInfo) {
        UserVo user = loginService.loginUser(loginInfo);
        String view = "";
        if (user == null) {
            view = "redirect:/triplan/loginform?errCode=1";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("session_id", user.getId());               // 세션에 id 넣음
            session.setAttribute("session_nickname", user.getNickname());   // 세션에 nickname 넣음
            view = "redirect:/triplan/main";
        }
        return view;
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/triplan/main";
    }

    // ID 중복확인
    @PostMapping("/checkId")
    public @ResponseBody Integer checkId(@RequestParam String email) {
        Integer count = loginService.countId(email);
        return count;
    }

    // 메일 전송
    @PostMapping("/mail")
    public String sendMail(MailVo mailVo) {
        loginService.sendTempPwMail(mailVo);
        return "redirect:/triplan/loginform";
    }

    // 회원가입: id 체크(중복/탈퇴)
    @PostMapping("/checkJoinId")
    public @ResponseBody int[] checkJoinId(@RequestParam String id) {
        int[] result = new int[2];
        int checkOverlapId = loginService.countId(id);
        int checkDropId = loginService.checkDropId(id);
        result[0] = checkOverlapId;
        result[1] = checkDropId;
        return result;
    }

    // 회원가입
    @PostMapping("/join")
    public String insertUser(Model model, UserVo user) {
        loginService.insertUser(user);
        return "redirect:/triplan/loginform?join=1";
    }
}
