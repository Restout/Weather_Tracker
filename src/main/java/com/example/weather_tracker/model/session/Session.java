package com.example.weather_tracker.model.session;

import com.example.weather_tracker.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "Users", name = "Sessions")
public class Session {
    public Session(User userId, Date experationDate) {
        this.userId = userId;
        this.experationDate = experationDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "UserID")
    private User userId;
    @Column(name = "ExpiresAt")
    private Date experationDate;

}
