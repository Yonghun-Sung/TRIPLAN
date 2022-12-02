package com.site.triplan.service;

import com.site.triplan.mapper.AdminMapper;
import com.site.triplan.vo.AdminVo;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 자동생성 및 final 변수 의존관계 자동 설정
public class AdminService implements UserDetailsService{    // security에서 지정한 형식 = UserDetailsService

    private final AdminMapper adminMapper;

//    public AdminService(AdminMapper adminMapper) {
//
//        this.adminMapper = adminMapper;
//    }

    public List<UserVo> postAllUser() {         // 전체회원
        return adminMapper.findAll();}
    public List<UserVo> postBanUser() {         // 정지회원
        return adminMapper.findBan(); }
    public List<UserVo> postDropUser() {        // 탈퇴회원
        return adminMapper.findDrop(); }
    public List<ReportVo> postUnreport() {      // 미처리신고
        return adminMapper.findUnreport(); }
    public List<ReportVo> postReport() {        // 처리내역
        return adminMapper.findReport(); }




    public void processReport(ReportVo reportVo) {     // 미처리신고 신고(승인, 반려)
        reportVo.setAdmin_code(1);
        adminMapper.processReport(reportVo);
    }

    public void processedReport(ReportVo reportVo){     // 처리내역 (철회, 유지)

        adminMapper.processedReport(reportVo);
    }

    public List<UserVo> weeklyNewbie(){
        return adminMapper.weeklyNewbie();
    }       // 주간 신규 회원

    public List<UserVo> montlyNewbie(){
        return adminMapper.monthlyNewbie();
    }       // 월간 신규 회원




    @Override
    public AdminVo loadUserByUsername(String id) throws UsernameNotFoundException{
        AdminVo adminVo = adminMapper.getAdminAccount(id);                // 여기고 받은 유저 패스워드와 비교하여 로그인 인증
        System.out.println(adminVo);
        if (adminVo == null) {
            throw new UsernameNotFoundException("User not authorized");
        }
        return  adminVo;
    }

}
