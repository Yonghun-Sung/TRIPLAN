package com.site.triplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/triplan")
public class MainController {

    @RequestMapping("/main")
    public String user_main() {
        return "user_main";
    }

    @RequestMapping("/planlist")
    public String planlist() {
        return "user_plan_list";
    }

    @RequestMapping("/loginform")
    public String loginform() {
        return "user_loginform";
    }

    @RequestMapping("/joinform")
    public String joinform() {
        return "user_joinform";
    }

    @RequestMapping("/mypage/like")
    public String mypagelike() {
        return "user_mypage_like";
    }

    @RequestMapping("/mypage/reply")
    public String mypagereply() {
        return "user_mypage_reply";
    }

    @RequestMapping("/mypage/")
    public String mypage() {
        return "user_mypage_main";
    }

}
