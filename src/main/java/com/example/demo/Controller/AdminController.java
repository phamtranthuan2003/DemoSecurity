package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/blogsList")
    public String showBlogsList(Model model) {
        return "admin/blogsList";
    }

}
