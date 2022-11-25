package com.site.triplan.controller;

import com.site.triplan.service.AdminService;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/triplan")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
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
            List<UserVo> userVoList = adminService.postAllUser();
            model.addAttribute("posts", userVoList);
        return "admin_member_all";
    }

    @RequestMapping("/memberBan")
    public String admin_member_ban(Model model) {
        List<UserVo> userVoList= adminService.postBanUser();    // 정지회원 데이터 받아오는 함수
        model.addAttribute("posts", userVoList);    // 정지회원 데이터 전달
        return "admin_member_ban";
    }

    @RequestMapping("/memberDrop")
    public String admin_member_drop(Model model) {
        List<UserVo> userVoList = adminService.postDropUser();
        model.addAttribute("posts", userVoList);
        return "admin_member_drop";
    }

    @RequestMapping("/reportUnproc")
    public String admin_report_unproc(Model model) {
        List<ReportVo> reportVoList = adminService.postUnreport();
        model.addAttribute("posts", reportVoList);
        return "admin_report_unproc";
    }

    @RequestMapping("/reportProc")
    public String admin_report_proc() {
        return "admin_report_proc";
    }

}
