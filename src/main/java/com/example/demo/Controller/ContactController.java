package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.Entity.ContactForm;
import com.example.demo.Service.EmailService;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendContact(@ModelAttribute ContactForm contactForm, RedirectAttributes redirectAttributes) {
        emailService.sendContactEmail("viet532k3@gmail.com", contactForm);
        redirectAttributes.addFlashAttribute("message", "Gửi thông tin liên hệ thành công!");
        return "redirect:/lien-he";
    }
}
