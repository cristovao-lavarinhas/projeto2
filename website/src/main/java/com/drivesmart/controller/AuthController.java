// src/main/java/com/drivesmart/controller/AuthController.java
package com.drivesmart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    String login(){
        return "auth/login";
    }

    @GetMapping("/register")
    String register(){
        return "auth/register";
    }

    @GetMapping("/logout")
    String logout(){
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    String dashboard(){
        return "dashboard";
    }
}
