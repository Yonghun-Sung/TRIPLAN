package com.site.triplan.controller;

import com.google.gson.Gson;
import com.site.triplan.service.AdminService;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

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
        List<ReportVo> reportVoList = adminService.postUnreport();      // 미처리신고 데이터 전달
        model.addAttribute("unreport",reportVoList);

        return "admin_main";
    }

    @RequestMapping("/memberAll")
    public String admin_member_all(Model model) {
            List<UserVo> userVoList = adminService.postAllUser();   // 전체회원 데이터 전달
            model.addAttribute("posts", userVoList);    
        return "admin_member_all";
    }

    @RequestMapping("/memberBan")
    public String admin_member_ban(Model model) {
        List<UserVo> userVoList= adminService.postBanUser();    // 정지회원 데이터 전달
        model.addAttribute("posts", userVoList);    
        return "admin_member_ban";
    }

    @RequestMapping("/memberDrop")
    public String admin_member_drop(Model model) {
        List<UserVo> userVoList = adminService.postDropUser();  // 탈퇴회원 데이터 전달
        model.addAttribute("posts", userVoList);    
        return "admin_member_drop";
    }

    @RequestMapping("/reportUnproc")
    public String admin_report_unproc(Model model) {
        List<ReportVo> reportVoList = adminService.postUnreport();  // 미처리신고 데이터 전달
        model.addAttribute("unreport", reportVoList);   
        return "admin_report_unproc";
    }

    @RequestMapping("/reportProc")
    public String admin_report_proc(Model model) {
        List<ReportVo> reportVoList = adminService.postReport();    // 처리내역 데이터 전달
        model.addAttribute("report",reportVoList);      
        return "admin_report_proc";
    }


    @PostMapping("/processReport")
    public String processReport(ReportVo reportVo) {
        adminService.processReport(reportVo);
        return "/triplan/reportUnproc";
    }

    @PutMapping("/processedReport") 
    public String processedReport(@RequestBody ReportVo reportVo) {
        adminService.processedReport(reportVo);
        return "/triplan/reportProc";
    }

    @RequestMapping(value = "newbie", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String monthlyNewbie(Locale locale, Model model){
        System.out.println("in");
        Gson gson = new Gson();
        List<UserVo> userVoList = adminService.monthlyNewbie();
        String ret = gson.toJson(userVoList);
        System.out.println(ret);
        return ret;
    }
}
