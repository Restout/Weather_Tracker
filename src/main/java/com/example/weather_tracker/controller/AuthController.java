package com.example.weather_tracker.controller;

import com.example.weather_tracker.exceptions.NullObjectException;
import com.example.weather_tracker.model.user.User;
import com.example.weather_tracker.model.user.UserOut;
import com.example.weather_tracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping(value = {"/login"})
    public String returnLogPage(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String returnRegistrationPage(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public ResponseEntity registrationNewUser( User user) {
        Logger logger = Logger.getLogger("loger");
        try {
            UserOut userOut = authService.createUser(user);
            return ResponseEntity
                    .ok()
                    .body(userOut);
        } catch (NullObjectException e) {
            logger.log(Level.INFO, e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
