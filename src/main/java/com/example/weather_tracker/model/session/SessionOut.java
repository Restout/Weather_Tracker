package com.example.weather_tracker.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionOut {
    private Date expireDate;
    private String userLogin;

    public SessionOut(Session session) {
        this.expireDate = session.getExperationDate();
        this.userLogin = session.getUserId().getEmail();
    }
}
