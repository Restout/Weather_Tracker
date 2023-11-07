package com.example.weather_tracker.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoUserException extends Exception {
    String message;

}
