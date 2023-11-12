package com.example.weather_tracker.controller;

import com.example.weather_tracker.exceptions.NoUserException;
import com.example.weather_tracker.exceptions.NullObjectException;
import com.example.weather_tracker.exceptions.UserCredentialsException;
import com.example.weather_tracker.exceptions.UserExistException;
import com.example.weather_tracker.model.session.Session;
import com.example.weather_tracker.model.session.SessionOut;
import com.example.weather_tracker.model.user.User;
import com.example.weather_tracker.model.user.UserIn;
import com.example.weather_tracker.model.user.UserOut;
import com.example.weather_tracker.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registrationNewUser(User user, HttpServletResponse response, Model model) {
        Logger logger = Logger.getLogger("loger");
        try {
            UserOut userOut = authService.createUser(user);
            response.addHeader("User", userOut.toString());
            return "redirect:/login";
        } catch (NullObjectException e) {
            logger.log(Level.INFO, e.getMessage());//implement bindingresults to logic
            model.addAttribute("error", e.getMessage());
            return "registration";
        } catch (UserExistException e) {
            logger.log(Level.INFO, e.getMessage());//implement bindingresults to logic
            model.addAttribute("error", e.getMessage());
            return "registration";
        }
    }

    @PostMapping("/login")
    public String authenticateUser(UserIn user, HttpServletResponse response, Model model) {
        Logger logger = Logger.getLogger("loger");
        try {
            Session session = authService.creatSessionForUser(user);
            Cookie cookie = new Cookie("Authentication", String.valueOf(session.getId()));
            cookie.setMaxAge((int) (session.getExperationDate().getTime() - System.currentTimeMillis()) / 1000);
            cookie.setPath("/");
            response.addCookie(cookie);
            SessionOut sessionOut = new SessionOut(session);
            response.addHeader("Session", sessionOut.toString());
            return "redirect:/";
        } catch (UserCredentialsException e) {
            logger.log(Level.INFO, e.getMessage());//implement bindingresults to logic
            model.addAttribute("error", e.getMessage());
            return "login";

        } catch (NoUserException e) {
            logger.log(Level.INFO, e.getMessage());//implement bindingresults to logic
            model.addAttribute("error", e.getMessage());
            return "login";
        }

    }

    @GetMapping("/logout")
    public String logOut(@CookieValue(name = "Authentication") Cookie cookie, HttpServletResponse response) {
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
