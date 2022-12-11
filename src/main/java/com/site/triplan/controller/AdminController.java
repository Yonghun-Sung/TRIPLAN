package com.site.triplan.controller;

import com.google.gson.Gson;
import com.site.triplan.service.AdminService;
import com.site.triplan.vo.AdminVo;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/triplan")
public class AdminController {

    private AdminService adminService;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();      // 암호화

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping("/adminLogin")                                                // 관리자 로그인 페이지
    public String admin_loginform() {
        return "admin_loginform";
    }

    @RequestMapping("/admin")                                                 // 관리자 메인
    public String admin_main(Model model, Authentication authentication, HttpServletRequest request) {
        AdminVo adminVo = (AdminVo) authentication.getPrincipal();            // security, userDetail 객체를 가져옴

        HttpSession session = request.getSession();
        session.setAttribute("session_admin_id", adminVo.getId());
        session.setAttribute("session_admin_name", adminVo.getName());

        List<ReportVo> reportVoList = adminService.postUnreport();
        model.addAttribute("unreport",reportVoList);
        return "admin_main";
    }

    @RequestMapping("/adminInfo")                                             // 관리자 비밀번호 변경 페이지
    public String admin_myinfo(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_admin_id");
        String name = (String)session.getAttribute("session_admin_name");

        AdminVo adminVo = adminService.loadUserByUsername(id);
        model.addAttribute("adminprofile", adminVo);
        model.addAttribute("session_name", name);
        return "admin_myinfo";
    }

    @RequestMapping(value = "/adminInfo", method = RequestMethod.POST)        //  관리자 비밀번호 변경 페이지 - 비밀번호 확인
    public @ResponseBody String CheckId(Model model, HttpServletRequest request, @RequestBody AdminVo AdminVo){
        HttpSession session = request.getSession();
        String id = (String)session.getAttribute("session_admin_id");
        String check = "0";

        AdminVo adminVo = adminService.loadUserByUsername(id);
        model.addAttribute("adminprofile", adminVo);

        if(!bCryptPasswordEncoder.matches(AdminVo.getPw(), adminVo.getPw())){   // AdminVo = 입력(암호화x), adminVo = db(암호화)
            check = "1";
            return check;
        }
        return check;
    }

    @PutMapping("/adminUpdate")                                               // 관리자 비밀번호 변경 페이지 - db 수정
    @ResponseBody
    public String adminUpdate(@RequestBody AdminVo adminVo){
//        System.out.println(adminVo.getPw());
        String pw = bCryptPasswordEncoder.encode(adminVo.getPw());            // 입력 비밀번호 암호화
        adminVo.setPw(pw);
//        System.out.println(adminVo.getPw());
        adminService.updatePw(adminVo);
        return "success";
    }

    @RequestMapping("/admin/memberAll")                                       // 전체회원
    public String admin_member_all(Model model) {
            List<UserVo> userVoList = adminService.postAllUser();
            model.addAttribute("posts", userVoList);    
        return "admin_member_all";
    }

    @RequestMapping("/admin/memberBan")                                       // 영구정지회원
    public String admin_member_ban(Model model) {
        List<UserVo> userVoList= adminService.postBanUser();
        model.addAttribute("posts", userVoList);    
        return "admin_member_ban";
    }

    @RequestMapping("/admin/memberDrop")                                      // 탈퇴회원
    public String admin_member_drop(Model model) {
        List<UserVo> userVoList = adminService.postDropUser();
        model.addAttribute("posts", userVoList);    
        return "admin_member_drop";
    }

    @RequestMapping("/admin/reportUnproc")                                    // 미처리신고
    public String admin_report_unproc(Model model) {
        List<ReportVo> reportVoList = adminService.postUnreport();
        model.addAttribute("unreport", reportVoList);   
        return "admin_report_unproc";
    }

    @RequestMapping("/admin/reportProc")                                      // 처리내역
    public String admin_report_proc(Model model) {
        List<ReportVo> reportVoList = adminService.postReport();
        model.addAttribute("report",reportVoList);      
        return "admin_report_proc";
    }
    
    @PostMapping("/processReport")                                      // 미처리신고 - 신고 승인
    public String processReport(ReportVo reportVo) {
        adminService.processReport(reportVo);
        return "/triplan/admin/reportUnproc";
    }

    @PutMapping("/processedReport")                                     // 처리내역 - 신고 철회
    public String processedReport(@RequestBody ReportVo reportVo) {
        adminService.processedReport(reportVo);
        return "/triplan/admin/reportProc";
    }

    @RequestMapping(value = "weeklynewbie", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String weeklyNewbie(Locale locale, Model model){       // 주간 신규 회원
//        System.out.println("in");
        Gson gson = new Gson();
        List<UserVo> userVoList = adminService.weeklyNewbie();
        String ret = gson.toJson(userVoList);                                   
//        System.out.println(ret);
        return ret;                                                             // 주간신규회원 리스트 Json형식으로 admin_LineChart에 전달
    }

    @RequestMapping(value = "montlynewbie", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public @ResponseBody String monthlyNewbie(Locale locale, Model model){      // 월간 신규 회원
//        System.out.println("in");
        Gson gson = new Gson();
        List<UserVo> userVoList = adminService.montlyNewbie();
        String ret = gson.toJson(userVoList);                                   
//        System.out.println(ret);
        return ret;                                                             // 월간신규회원 리스트 Json형식으로 admin_LineChart에 전달
    }

}
