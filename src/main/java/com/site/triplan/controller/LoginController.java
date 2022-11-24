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
    public String loginUser(Model model, HttpServletRequest request, UserVo user) {
        String id = loginService.loginUser(user);
        String view = "";

        if (id == null) {
            model.addAttribute("errCode", "1");
            view = "redirect:/triplan/loginform?errCode=1";
        } else {
            // id 세션에 심어두고, 메인으로 이동
            HttpSession session = request.getSession();
            session.setAttribute("id", id);         // 세션에 id 심어놓음
            String checkid = (String)session.getAttribute("id");
            System.out.println(checkid);
            view = "redirect:/triplan/main";
        }
        return view;
    }

    // 헤더 (로그인/비로그인 상태)
    @GetMapping("/user_header")
    public String showUserHeader(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("id");
        if (id != null) {
            String nickname = loginService.getNickname(id);
            model.addAttribute("nickname", nickname);
        }
        return "user_header";
    }


    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/triplan/main";
    }
}
