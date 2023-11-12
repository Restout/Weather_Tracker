package com.example.weather_tracker.service.interfaces;

import com.example.weather_tracker.model.Coordinates;
import com.example.weather_tracker.model.location.Location;

import java.util.List;

public interface WeatherService {
    List<Location> findAllLocations(String city);

    Location findLocationByCoordinates(Coordinates coordinates);

    void deleteLocation(String city);

    Location addLocation(Location location);

}
