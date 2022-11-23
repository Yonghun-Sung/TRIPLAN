package com.site.triplan.controller;

import com.site.triplan.service.UserService;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/triplan")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public String admin_main() {
        return "admin_main";
    }

    @RequestMapping("/adminLogin")
    public String admin_loginform() {
        return "admin_loginform";
    }

    @RequestMapping("/adminInfo")
    public String admin_myinfo() {
        return "admin_myinfo";
    }

    @RequestMapping("/memberAll")
    public String admin_member_all(Model model) {
            List<UserVo> userVoList = userService.getAllUser();
            model.addAttribute("posts", userVoList);
        return "admin_member_all";
    }

//    @RequestMapping(value = "/triplan/memberAll")
//    public String index(Model model){
//        List<UserVo> userVoList = userService.getAllUser();
//        return "/trinplan/adminAll";
//    }

    @RequestMapping("/memberBan")
    public String admin_member_ban() {
        return "admin_member_ban";
    }

    @RequestMapping("/memberDrop")
    public String admin_member_drop() {
        return "admin_member_drop";
    }

    @RequestMapping("/reportUnproc")
    public String admin_report_unproc() {
        return "admin_report_unproc";
    }

    @RequestMapping("/reportProc")
    public String admin_report_proc() {
        return "admin_report_proc";
    }


}
