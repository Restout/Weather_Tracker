package com.example.weather_tracker.service;

import com.example.weather_tracker.exceptions.NoUserException;
import com.example.weather_tracker.exceptions.NullObjectException;
import com.example.weather_tracker.exceptions.UserCredentialsException;
import com.example.weather_tracker.exceptions.UserExistException;
import com.example.weather_tracker.model.session.Session;
import com.example.weather_tracker.model.user.User;
import com.example.weather_tracker.model.user.UserIn;
import com.example.weather_tracker.model.user.UserOut;
import com.example.weather_tracker.repository.SessionRepository;
import com.example.weather_tracker.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public UserOut createUser(User user) throws NullObjectException {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()){
            throw  new UserExistException("User already Exist");
        }
        User user1 = userRepository.save(user);
        if (!user1.equals(user)) {
            throw new NullObjectException("User Not Found");
        }
        return new UserOut(user1);
    }

    private boolean authorizeUser(UserIn user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        return user.getPassword().equals(optionalUser.get().getPassword());
    }

    private  Optional<Session> findNonExpiredSession(User user) {
        Iterable<Session> sessions = sessionRepository.findAllByUserId(user);
        for (Session s : sessions) {
            if (s.getExperationDate().compareTo(new Date(System.currentTimeMillis())) > 0) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }

    public Session creatSessionForUser(UserIn user) throws UserCredentialsException, NoUserException {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isEmpty()) {
            throw new NoUserException("Email is not registrant");
        }
        if (!authorizeUser(user)) {
            throw new UserCredentialsException("Login or Password are incorrect");
        }
        Optional<Session> optionalSession = findNonExpiredSession(optionalUser.get());
        if (optionalSession.isPresent()) {
            return optionalSession.get();
        }
        Session session = new Session(optionalUser.get(), new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10));
        return sessionRepository.save(session);
    }

    public boolean validateSession(@CookieValue(name = "Authorization") Cookie cookie) {
        Session session = sessionRepository.findById(Integer.parseInt(cookie.getValue())).get();
        if (session.getExperationDate().getTime() <= System.currentTimeMillis()) {
            return false;
        }
        return true;
    }

}
