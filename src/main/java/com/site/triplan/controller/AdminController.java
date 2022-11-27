package com.site.triplan.controller;

import com.site.triplan.service.AdminService;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/triplan")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @RequestMapping("/adminLogin")
    public String admin_loginform() {
        return "admin_loginform";
    }

    @RequestMapping("/adminInfo")
    public String admin_myinfo() {
        return "admin_myinfo";
    }

    @RequestMapping("/admin")
    public String admin_main(Model model) {
        List<ReportVo> reportVoList = adminService.postUnreport();      // 미처리신고 데이터 받아오기
        model.addAttribute("unreport",reportVoList);        // 미처리신고 데이터 전달
        return "admin_main";
    }

    @RequestMapping("/memberAll")
    public String admin_member_all(Model model) {
            List<UserVo> userVoList = adminService.postAllUser();   // 전체회원 데이터 받아오기
            model.addAttribute("posts", userVoList);    // 전체회원 데이터 전달
        return "admin_member_all";
    }

    @RequestMapping("/memberBan")
    public String admin_member_ban(Model model) {
        List<UserVo> userVoList= adminService.postBanUser();    // 정지회원 데이터 받아오기
        model.addAttribute("posts", userVoList);    // 정지회원 데이터 전달
        return "admin_member_ban";
    }

    @RequestMapping("/memberDrop")
    public String admin_member_drop(Model model) {
        List<UserVo> userVoList = adminService.postDropUser();  // 탈퇴회원 데이터 받아오기
        model.addAttribute("posts", userVoList);    // 탈퇴회원 데이터 전달
        return "admin_member_drop";
    }

    @RequestMapping("/reportUnproc")
    public String admin_report_unproc(Model model) {
        List<ReportVo> reportVoList = adminService.postUnreport();  // 미처리신고 데이터 받아오기
        model.addAttribute("unreport", reportVoList);   // 미처리신고
        return "admin_report_unproc";
    }

    @RequestMapping("/reportProc")
    public String admin_report_proc(Model model) {
        List<ReportVo> reportVoList = adminService.postReport();    // 처리신고 데이터 받아오기
        model.addAttribute("report",reportVoList);      // 처리신고 데이터 전달
        return "admin_report_proc";
    }


    @PostMapping("/processReport")  // 신고처리
    public String processReport(ReportVo reportVo) {
        adminService.processReport(reportVo);
        return "/triplan/reportUnproc";
    }

}
