//package com.example.demo.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequestMapping("/tms")
//public class PageController {
//
//    @GetMapping("/login")
//    public String loginPage() {
//    	System.out.println("Login controller");
//        return "login"; // login.jsp
//    }
//
//    @GetMapping("/dashboard")
//    public String dashboardPage(Model model, Authentication auth) {
//    	System.out.println("Dashboard controller");
//        //String role = auth.getAuthorities().iterator().next().getAuthority(); 
//        // ROLE_USER or ROLE_ADMIN
//    	if (auth != null) {
//            String role = auth.getAuthorities().iterator().next().getAuthority();
//            model.addAttribute("role", role);
//        } else {
//            model.addAttribute("role", "GUEST"); // fallback
//        }
//        
//
//        return "dashboard";
//    }
//}