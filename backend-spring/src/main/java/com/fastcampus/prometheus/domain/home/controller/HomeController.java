package com.fastcampus.prometheus.domain.home.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index(HttpSession session, Model model) {
        Object jws = session.getAttribute("jws");
        model.addAttribute("jws", jws);
        return "main";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ 세션 초기화
        return "redirect:/";
    }


}
