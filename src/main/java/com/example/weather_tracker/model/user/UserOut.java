package com.example.weather_tracker.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOut {
    private String name;
    private String email;

    public UserOut(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
