package com.example.weather_tracker.controller;

import com.example.weather_tracker.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@Controller
public class BaseController {
    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String getHomePage(Model model, HttpServletRequest request) {
        Optional<Cookie> cookie = Arrays
                .stream(request.getCookies())
                .filter((cook) -> cook.getName().equals("Authentication")).findFirst();
        if (cookie.isPresent())
            if (authService.validateSession(cookie.get())) {
                model.addAttribute("authorize", true);
                return "home";
            }
        model.addAttribute("authorize", false);
        return "home";
    }
}
