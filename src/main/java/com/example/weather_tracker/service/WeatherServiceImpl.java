package com.example.weather_tracker.service;

import com.example.weather_tracker.model.Coordinates;
import com.example.weather_tracker.model.location.Location;
import com.example.weather_tracker.service.interfaces.WeatherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {


    @Override
    public List<Location> findAllLocations(String city) {
        return null;
    }

    @Override
    public Location findLocationByCoordinates(Coordinates coordinates) {
        return null;
    }

    @Override
    public void deleteLocation(String city) {

    }

    @Override
    public Location addLocation(Location location) {
        return null;
    }
}
