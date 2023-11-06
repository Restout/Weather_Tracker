package com.example.weather_tracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping(value={"/login"})
    public String returnLogPage(Model model){
        return "login";
    }
}
