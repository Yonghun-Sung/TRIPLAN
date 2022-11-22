package com.site.triplan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/triplan")
public class AdminController {

    @RequestMapping("/admin")
    public String admin_main() {
        return "admin_main";
    }
}
