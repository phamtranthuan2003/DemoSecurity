package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import com.example.demo.Entity.Blog;
import com.example.demo.Service.BlogService;

@Controller
public class UserHomeController {

    private final BlogService blogService;

    // Constructor Injection cho BlogService
    public UserHomeController(BlogService blogService) {
        this.blogService = blogService;
    }

    // Trang chủ
    @GetMapping("/")
    public String home() {
        return "user/home";
    }

    // Trang liên hệ
    @GetMapping("/lien-he")
    public String lienhe() {
        return "user/lienhe";
    }

    // Trang giới thiệu
    @GetMapping("/gioi-thieu")
    public String gioithieu() {
        return "user/gioithieu";
    }

    // Trang tuyển dụng (hiển thị danh sách blog)
    @GetMapping("/tuyen-dung")
    public String tuyendung(Model model) {
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "user/tuyendung";
    }

    // Trang hình ảnh hoạt động
    @GetMapping("/hinh-anh-hoat-dong")
    public String hinhanhhoatdong() {
        return "user/hinhanhhoatdong";
    }

    // (Tuỳ chọn mở rộng) Trang chi tiết blog
    // @GetMapping("/blogs/{id}")
    // public String blogDetail(@PathVariable Long id, Model model) {
    //     Blog blog = blogService.findById(id);
    //     if (blog == null) return "redirect:/tuyen-dung";
    //     model.addAttribute("blog", blog);
    //     return "user/blog-detail";
    // }
}
