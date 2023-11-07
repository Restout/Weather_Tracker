package com.example.weather_tracker.exceptions;

import lombok.Getter;

@Getter
public class NullObjectException extends Exception {
    private String message;

    public NullObjectException(String message) {

        this.message = message;
    }

}
