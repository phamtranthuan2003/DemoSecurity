package com.example.demo.Controller;

import java.security.Security;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.SecurityServiceEntity;
import com.example.demo.Service.BlogService;
import com.example.demo.Service.SecurityService;

@Controller
public class UserHomeController {

    private final BlogService blogService;
    private final SecurityService securityService;

    public UserHomeController(BlogService blogService, SecurityService securityService) {
        this.blogService = blogService;
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String home() {
        return "user/home";
    }

    @GetMapping("/lien-he")
    public String lienhe() {
        return "user/lienhe";
    }

    @GetMapping("/gioi-thieu")
    public String gioithieu() {
        return "user/gioithieu";
    }

    @GetMapping("/tuyen-dung")
    public String tuyendung(Model model) {
        List<Blog> blogs = blogService.findAll();
        blogs.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        model.addAttribute("blogs", blogs);
        return "user/tuyendung";
    }

    @GetMapping("/hinh-anh-hoat-dong")
    public String hinhanhhoatdong() {
        return "user/hinhanhhoatdong";
    }

    @GetMapping("/blog/{id}")
    public String getBlogDetail(@PathVariable Long id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        return "user/blogDetail";
    }

    @GetMapping("/dich-vu")
    public String dichvu(Model model) {
        List<SecurityServiceEntity> services = securityService.findAll();
        services.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        model.addAttribute("services", services);
        return "user/dichvu";
    }

    @GetMapping("/dich-vu/{id}")
    public String getServiceDetail(@PathVariable Long id, Model model) {
        SecurityServiceEntity service = securityService.findById(id);
        model.addAttribute("service", service);
        return "user/serviceDetail";
    }
}
