package com.site.triplan.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {
    String id;
    String pw;
    String name;
    String nickname;
    Integer user_code;


}
