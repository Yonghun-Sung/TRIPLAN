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

}
