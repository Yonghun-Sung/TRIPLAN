package com.site.triplan.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportVo {
    String reportUser;
    String reportedUser;
    String content;
    String reason;
    String dt;
    String code;
    String reg_code;
    String result_code;
    String admin_code;
    String user_code;
}
