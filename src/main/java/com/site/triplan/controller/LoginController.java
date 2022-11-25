package com.site.triplan.controller;

import com.site.triplan.service.LoginService;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

        if (user.getId() == null) {
            model.addAttribute("errCode", "1");
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
}
