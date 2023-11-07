package com.example.weather_tracker.repository;

import com.example.weather_tracker.model.session.Session;
import com.example.weather_tracker.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {
    Iterable<Session> findAllByUserId(User userId);
}
