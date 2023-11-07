package com.example.weather_tracker.repository;

import com.example.weather_tracker.model.user.User;
import jakarta.persistence.NamedNativeQueries;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
