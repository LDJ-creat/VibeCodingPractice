package com.chuanzhi.health.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.chuanzhi.health.dao.impl.UserDaoImpl;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.util.DBUtil;
import com.chuanzhi.health.util.PasswordUtil;

class UserDaoImplTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        // Clean up before each test
        cleanupTestUser("testuser");
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        cleanupTestUser("testuser");
    }

    private void cleanupTestUser(String username) {
        String sql = "DELETE FROM users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addAndFindByUsername() {
        // 1. Test add
        User user = new User();
        user.setUsername("testuser");
        user.setPassword(PasswordUtil.hashPassword("password123"));
        user.setUname("测试用户");
        user.setTel("13800138000");
        user.setSex("男");
        user.setBir(new Date());
        user.setIdcard("110101200001011234");
        user.setAddress("测试地址");
        user.setDep("测试科室");
        user.setLev("测试职位");
        user.setAvatar("avatar.png");

        userDao.add(user);

        // 2. Test findByUsername
        User foundUser = userDao.findByUsername("testuser");
        assertNotNull(foundUser, "User should be found after adding");
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("测试用户", foundUser.getUname());
        assertTrue(PasswordUtil.checkPassword("password123", foundUser.getPassword()), "Password should match");
    }

    @Test
    void findByUsername_NotFound() {
        User foundUser = userDao.findByUsername("nonexistentuser");
        assertNull(foundUser, "User should not be found");
    }
}