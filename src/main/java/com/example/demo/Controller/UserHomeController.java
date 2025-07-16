package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.example.demo.Entity.Blog;
import com.example.demo.Service.BlogService;

@Controller
public class UserHomeController {

    private final BlogService blogService;

    public UserHomeController(BlogService blogService) {
        this.blogService = blogService;
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
}
