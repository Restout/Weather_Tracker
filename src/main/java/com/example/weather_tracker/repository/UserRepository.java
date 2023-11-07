package com.example.weather_tracker.repository;

import com.example.weather_tracker.model.user.User;
import jakarta.persistence.NamedNativeQueries;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
