package com.chuanzhi.health.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chuanzhi.health.dao.UserDao;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.util.PasswordUtil;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private String plainPassword = "password123";

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1);
        testUser.setUsername("testuser");
        testUser.setPassword(PasswordUtil.hashPassword(plainPassword));
    }

    @Test
    void login_Success() {
        // Arrange
        when(userDao.findByUsername("testuser")).thenReturn(testUser);

        // Act
        User result = userService.login("testuser", plainPassword);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userDao, times(1)).findByUsername("testuser");
    }

    @Test
    void login_UserNotFound() {
        // Arrange
        when(userDao.findByUsername("unknownuser")).thenReturn(null);

        // Act
        User result = userService.login("unknownuser", "password123");

        // Assert
        assertNull(result);
        verify(userDao, times(1)).findByUsername("unknownuser");
    }

    @Test
    void login_WrongPassword() {
        // Arrange
        when(userDao.findByUsername("testuser")).thenReturn(testUser);

        // Act
        User result = userService.login("testuser", "wrongpassword");

        // Assert
        assertNull(result);
        verify(userDao, times(1)).findByUsername("testuser");
    }

    @Test
    void register_Success() {
        // Arrange
        when(userDao.findByUsername("newuser")).thenReturn(null);
        doNothing().when(userDao).add(any(User.class));

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");

        // Act
        boolean result = userService.register(newUser);

        // Assert
        assertTrue(result);
        verify(userDao, times(1)).findByUsername("newuser");
        verify(userDao, times(1)).add(any(User.class));
    }

    @Test
    void register_UserAlreadyExists() {
        // Arrange
        when(userDao.findByUsername("testuser")).thenReturn(testUser);

        User existingUser = new User();
        existingUser.setUsername("testuser");
        existingUser.setPassword("anypassword");

        // Act
        boolean result = userService.register(existingUser);

        // Assert
        assertFalse(result);
        verify(userDao, times(1)).findByUsername("testuser");
        verify(userDao, never()).add(any(User.class));
    }
}