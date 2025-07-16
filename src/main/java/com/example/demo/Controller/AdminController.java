package com.example.demo.Controller;

import com.example.demo.Entity.Blog;
import com.example.demo.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/blogsList")
    public String showBlogsList(Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "admin/blogsList";
    }

    @GetMapping("/blogs/create")
    public String createBlogList() {
        return "admin/createBlogsList";
    }

    @PostMapping("/blogs/save")
    public String saveBlog(@RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile imageFile) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setAuthor(author);
        blog.setContent(content);
        blog.setCreatedAt(LocalDateTime.now());

        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                Files.createDirectories(Paths.get(uploadDir));

                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                blog.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        blogService.save(blog);
        return "redirect:/admin/blogsList";
    }

    @PostMapping("/blogs/upload-image")
    @ResponseBody
    public Map<String, Object> uploadImage(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("error", Map.of("message", "Tập tin trống"));
            return response;
        }

        try {
            // 📁 Đường dẫn upload ảnh CKEditor
            String uploadDir = System.getProperty("user.dir") + "/uploads/editor";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            response.put("uploaded", true);
            response.put("url", "/uploads/editor/" + fileName);
            return response;

        } catch (IOException e) {
            response.put("error", Map.of("message", "Lỗi khi lưu ảnh: " + e.getMessage()));
            return response;
        }
    }
    @GetMapping("/blogs/edit/{id}")
    public String editBlog(@PathVariable Long id, Model model) {
        Blog blog = blogService.findById(id);
        if (blog == null)
            return "redirect:/admin/blogsList";
        model.addAttribute("blog", blog);
        return "admin/editBlog";
    }

    @PostMapping("/blogs/update")
    public String updateBlog(@ModelAttribute Blog blog,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        // Lấy lại bản ghi gốc để giữ các giá trị không gửi từ form (như createdAt)
        Blog existing = blogService.findById(blog.getId());
        if (existing == null) {
            return "redirect:/admin/blogsList";
        }

        existing.setTitle(blog.getTitle());
        existing.setAuthor(blog.getAuthor());
        existing.setContent(blog.getContent());

        // Xử lý ảnh nếu có ảnh mới
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                Files.createDirectories(Paths.get(uploadDir));

                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                existing.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Nếu không có ảnh mới thì giữ nguyên ảnh cũ
        blogService.save(existing);
        return "redirect:/admin/blogsList";
    }

    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogService.delete(id);
        return "redirect:/admin/blogsList";
    }
}
