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
    String state;
    String dt;
    String code;
    Integer reg_code;
    Integer result_code;
    Integer admin_code;
    String user_code;
}
