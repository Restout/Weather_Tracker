package com.example.weather_tracker;

import com.example.weather_tracker.exceptions.NoUserException;
import com.example.weather_tracker.exceptions.NullObjectException;
import com.example.weather_tracker.exceptions.UserCredentialsException;
import com.example.weather_tracker.model.session.Session;
import com.example.weather_tracker.model.user.User;
import com.example.weather_tracker.model.user.UserIn;
import com.example.weather_tracker.model.user.UserOut;
import com.example.weather_tracker.service.AuthService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthServiceTests {
    @Autowired
    private AuthService authService;

    @TestConfiguration
    public static class testConfig {
        @Primary
        @Bean("TestDataSource")
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:db.sql")
                    .build();
        }
    }

    @BeforeEach
    private void generateNewDbTestData() {

    }

    @Test
    public void creatGoodUserTest() throws NullObjectException {
        User user = new User("Pavel", "pavlusha@mail.com", "1234");
        UserOut userOut = new UserOut(user);
        UserOut userTest = authService.createUser(user);
        assertEquals(userTest, userOut);
    }

    @Test
    public void creatBadUsers() throws NullObjectException {
        User user1 = new User(null, "pavlusha@mail.com", "1234");
        User user2 = new User("Pavel", null, "1234");
        User user3 = new User("Pavel", "pavlusha2@mail.com", null);
        assertEquals(new UserOut(user1), authService.createUser(user1));
    /*    assertThrows(SQLException.class, () -> {
            authService.createUser(user2);
        });
        assertThrows(SQLException.class, () -> {
            authService.createUser(user3);
        });*/
    }

    @Test
    public void testRegistration() throws NullObjectException, UserCredentialsException, NoUserException {
        User user1 = new User("Pavel", "pavlusha@mail.com", "12345");
        User user2 = new User("Pavel", "pavlusha@mail.com", "1234");
        User user3 = new User("Pavel", "pavlu@mail.com", "1234");
        authService.createUser(user1);
        Session session = authService.creatSessionForUser(new UserIn(user1.getEmail(), user1.getPassword()));
        assertThrows(UserCredentialsException.class, () -> {
            authService.creatSessionForUser(new UserIn(user2.getEmail(), user2.getPassword()));
        });
        assertThrows(NoUserException.class, () -> {
            authService.creatSessionForUser(new UserIn(user3.getEmail(), user3.getPassword()));
        });
        assertEquals(user1.getId(), authService.creatSessionForUser(new UserIn(user1.getEmail(), user1.getPassword()))
                .getUserId()
                .getId());
        assertEquals(session.getId(), authService.creatSessionForUser(new UserIn(user1.getEmail(), user1.getPassword())).getId());
    }

    @Test
    public void testBadValidationOfSession() throws NullObjectException, UserCredentialsException, NoUserException {
        User user1 = new User("Pavel", "pavlusha@mail.com", "12345");
        authService.createUser(user1);
        authService.creatSessionForUser(new UserIn(user1.getEmail(), user1.getPassword()));
        Cookie cookie = new Cookie("Authentiaction", String.valueOf(user1.getId()));
        cookie.setMaxAge(0);
        cookie.setPath("/");
        assertTrue(authService.validateSession(cookie));
    }


}
