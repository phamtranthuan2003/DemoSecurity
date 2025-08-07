package com.example.demo.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Customer;
import com.example.demo.Repository.CustomerRepository;

@Controller
public class LoginController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
            @RequestParam String password,
            Model model) {

        if (email.isEmpty() || password.isEmpty()) {
            model.addAttribute("Error", "Email hoặc mật khẩu không được để trống");
            return "login";
        }

        if (!customerRepository.existsByEmail(email)) {
            model.addAttribute("Error", "Email không tồn tại");
            return "login";
        }

        Optional<Customer> customer = customerRepository.findByEmail(email);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            return "redirect:/admin/home";
        } else {
            model.addAttribute("Error", "Mật khẩu không chính xác");
            return "login";
        }
    }
}
