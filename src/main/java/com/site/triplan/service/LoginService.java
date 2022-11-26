package com.site.triplan.service;

import com.site.triplan.mapper.LoginMapper;
import com.site.triplan.vo.MailVo;
import com.site.triplan.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

@Service
public class LoginService {
    private LoginMapper loginMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "TRIPLAN_ADMIN@triplan.com";

    public LoginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    // 로그인
    public UserVo loginUser(UserVo loginInfo) {
        UserVo user = loginMapper.loginUser(loginInfo);
        return user;
    }

    // ID 중복확인
    public Integer countId(String id) {
        Integer count = loginMapper.countId(id);
        return count;
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

    // 비밀번호 변경
    public void updatePw(String updatePw, String id) {
        loginMapper.updatePw(updatePw, id);
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

        updatePw(tempPw, mailVo.getAddress());

        mail.setTo(mailVo.getAddress());
        mail.setSubject(mailVo.getTitle());
        mail.setText(mailVo.getMessage());
        mail.setFrom(FROM_ADDRESS);
        mail.setReplyTo(FROM_ADDRESS);

        try {
            javaMailSender.send(mail);
        } catch (MailException e) {
            System.out.println("메일발송실패");
            e.printStackTrace();
        }
    }
}
