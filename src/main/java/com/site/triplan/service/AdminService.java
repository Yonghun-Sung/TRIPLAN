package com.site.triplan.service;

import com.site.triplan.mapper.AdminMapper;
import com.site.triplan.vo.AdminVo;
import com.site.triplan.vo.MailVo;
import com.site.triplan.vo.ReportVo;
import com.site.triplan.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "TRIPLAN_ADMIN@triplan.com";

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
        AdminVo adminVo = adminMapper.getAdminAccount(id);                // 여기서 받은 유저 패스워드와 비교하여 로그인 인증
        if (adminVo == null) {
            throw new UsernameNotFoundException("User not authorized");
        }
        return  adminVo;
    }

    // 비밀번호 변경
    public void updatePw(String id, String updatepw){
        adminMapper.updatePw(id, updatepw);
    }

    // 임시 비밀번호 생성
    public String createTempPw() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 14개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 14; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    // ID 중복확인
    public Integer countId(String id) {
        Integer count = adminMapper.countId(id);
        return count;
    }

    // 메일 전송
    public void sendTempPwMail(MailVo mailVo) {
        SimpleMailMessage mail = new SimpleMailMessage();


        String title = "TRIPLAN 임시비밀번호 안내 이메일입니다.";
        String tempPw = createTempPw();
        String message = "안녕하세요. TRIPLAN 임시비밀번호 안내 이메일입니다.\n\n"
                + "아래의 임시 비밀번호를 확인 후 로그인해주세요.\n\n"
                + tempPw
                + "\n\n로그인 후, 비밀번호를 변경해주세요.";
        mailVo.setTitle(title);
        mailVo.setMessage(message);

        updatePw(mailVo.getAddress(), bCryptPasswordEncoder.encode(tempPw));

        mail.setTo(mailVo.getAddress());
        mail.setSubject(mailVo.getTitle());
        mail.setText(mailVo.getMessage());
        mail.setFrom(FROM_ADDRESS);
        mail.setReplyTo(FROM_ADDRESS);

        try {
//            System.out.println(mail);
            javaMailSender.send(mail);
        } catch (MailException e) {
            System.out.println("메일발송실패");
            e.printStackTrace();
        }
    }
}
