package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserHomeController {
    
    @GetMapping("/")
    public String home(){
        return "user/home";
    }
    @GetMapping("/lien-he")
    public String lienhe(){
        return "user/lienhe";
    }
    @GetMapping("/gioi-thieu")
    public String gioithieu(){
        return "user/gioithieu";
    }
    @GetMapping("/tuyen-dung")
    public String tuyendung(){
        return "user/tuyendung";
    }
    @GetMapping("hinh-anh-hoat-dong")
    public String hinhanhhoatdong(){
        return "user/hinhanhhoatdong";
    }
}
