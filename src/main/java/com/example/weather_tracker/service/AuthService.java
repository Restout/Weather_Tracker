package com.example.weather_tracker.service;

import com.example.weather_tracker.exceptions.NullObjectException;
import com.example.weather_tracker.model.Session;
import com.example.weather_tracker.model.user.User;
import com.example.weather_tracker.model.user.UserIn;
import com.example.weather_tracker.model.user.UserOut;
import com.example.weather_tracker.repository.SessionRepository;
import com.example.weather_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public UserOut createUser(User user) throws NullObjectException {
        User user1 = userRepository.save(user);
        if (user1 == null) {
            throw new NullObjectException("User Not Found");
        }
        UserOut userOut = new UserOut(user1);
        return userOut;
    }

    public Session creatSessionForUser(UserIn user) {
        return null;
    }


}
