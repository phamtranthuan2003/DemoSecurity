package com.example.demo.Controller;

import com.example.demo.Entity.Blog;
import com.example.demo.Entity.Customer;
import com.example.demo.Entity.SecurityServiceEntity;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Service.BlogService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private final SecurityService serviceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AdminController(SecurityService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/home")
    public String home() {
        return "admin/home";
    }

    @GetMapping("/blogsList")
    public String showBlogsList(Model model) {
        model.addAttribute("blogs", blogService.findAll());
        return "admin/blog/blogsList";
    }

    @GetMapping("/blogs/create")
    public String createBlogList() {
        return "admin/blog/createBlogsList";
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
        return "redirect:/admin/blog/blogsList";
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
        return "admin/blog/editBlog";
    }

    @PostMapping("/blogs/update")
    public String updateBlog(@ModelAttribute Blog blog,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        Blog existing = blogService.findById(blog.getId());
        if (existing == null) {
            return "redirect:/admin/blogsList";
        }

        existing.setTitle(blog.getTitle());
        existing.setAuthor(blog.getAuthor());
        existing.setContent(blog.getContent());

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

        blogService.save(existing);
        return "redirect:/admin/blogsList";
    }

    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogService.delete(id);
        return "redirect:/admin/blogsList";
    }

    @GetMapping("/servicesList")
    public String servicesList(Model model) {
        model.addAttribute("services", serviceService.findAll());
        return "admin/service/servicesList";
    }

    @GetMapping("/services/create")
    public String createService() {
        return "admin/service/createService";
    }

    @PostMapping("/services/save")
    public String saveService(@RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile imageFile) {
        SecurityServiceEntity service = new SecurityServiceEntity();
        service.setTitle(title);
        service.setContent(content);
        service.setCreatedAt(LocalDateTime.now());

        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads";
                Files.createDirectories(Paths.get(uploadDir));

                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                service.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        serviceService.save(service);
        return "redirect:/admin/servicesList";
    }

    @PostMapping("/services/upload-image")
    @ResponseBody
    public Map<String, Object> handleEditorImageUpload(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("error", Map.of("message", "Tập tin trống"));
            return response;
        }

        try {
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

    @GetMapping("/services/edit/{id}")
    public String editService(@PathVariable Long id, Model model) {
        SecurityServiceEntity service = serviceService.findById(id);
        if (service == null)
            return "redirect:/admin/servicesList";
        model.addAttribute("service", service);
        return "admin/service/editService";
    }

    @PostMapping("/services/update")
    public String updateService(@ModelAttribute SecurityServiceEntity service,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

        SecurityServiceEntity existing = serviceService.findById(service.getId());
        if (existing == null) {
            return "redirect:/admin/servicesList";
        }

        existing.setTitle(service.getTitle());
        existing.setContent(service.getContent());

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

        serviceService.save(existing);
        return "redirect:/admin/servicesList";
    }

    @GetMapping("/services/delete/{id}")
    public String deleteService(@PathVariable("id") Long id) {
        serviceService.delete(id);
        return "redirect:/admin/servicesList";
    }

    @GetMapping("/customersList")
    public String customersList(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "admin/customerList";
    }

    @GetMapping("/signup")
    public String signup() {
        return "admin/signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String rePassword,
            @RequestParam String phone,
            Model model) {

        if (customerRepository.findByEmail(email).isPresent()) {
            model.addAttribute("Error", "Email đã được sử dụng");
            return "user/signup";
        }

        if (!password.equals(rePassword)) {
            model.addAttribute("Error", "Mật khẩu không khớp");
            return "admin/customerList";
        }

        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPhone(phone);

        customer.setPassword(passwordEncoder.encode(password));

        customerRepository.save(customer);
        return "redirect:/admin/customersList";
    }

    @GetMapping("/editCustomer/{id}")
    public String editCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            return "redirect:/admin/customersList";
        }
        model.addAttribute("customer", customer);
        return "admin/editCustomer";
    }

    @PostMapping("/editCustomer/{id}")
    public String updateCustomer(@PathVariable Long id,
            @ModelAttribute Customer customer,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String rePassword,
            Model model) {

        Customer existingCustomer = customerService.findById(id);
        if (existingCustomer == null) {
            return "redirect:/admin/customersList";
        }

        existingCustomer.setUsername(username);
        existingCustomer.setEmail(email);
        existingCustomer.setPhone(phone);

        if (password != null && !password.isBlank()) {
            if (!password.equals(rePassword)) {
                model.addAttribute("Error", "Mật khẩu không khớp");
                model.addAttribute("customer", existingCustomer);
                return "admin/editCustomer";
            }
            existingCustomer.setPassword(passwordEncoder.encode(password));
        }

        customerService.save(existingCustomer);
        return "redirect:/admin/customersList";
    }
}