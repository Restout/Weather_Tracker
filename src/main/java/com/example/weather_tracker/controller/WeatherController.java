package com.example.weather_tracker.controller;

import com.example.weather_tracker.service.WeatherServiceImpl;
import com.example.weather_tracker.service.interfaces.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    WeatherController() {
        weatherService = new WeatherServiceImpl();
    }

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${api-key}")
    private String key;

    @Value("${base-url}")
    private String url;

    @GetMapping("/weather")
    public ResponseEntity<String> searchWeather(@RequestParam String city) {
        StringBuilder getRequest = new StringBuilder(url).append("direct")
                .append("?   q=")
                .append(city)
                .append("&appid=")
                .append(key);
        ResponseEntity<String> response = restTemplate.getForEntity(getRequest.toString(), String.class);
        return response;
    }

}
