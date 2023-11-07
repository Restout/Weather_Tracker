package com.example.weather_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
   /* @GetMapping("/")
    public String getIndex(Model model) {
        List<Integer> testList = new ArrayList<>();
        testList.add(1);
        testList.add(2);
        testList.add(3);
        model.addAttribute("User", new User(1, "Pavel"));
        model.addAttribute("List", testList);
        model.addAttribute("message","Smth");
        return "index";
    }*/

    @GetMapping("/hello")
    public ResponseEntity<String> getHelloWithMessage(@RequestParam String Message) {
        return ResponseEntity.ok().body(Message);
    }
}
