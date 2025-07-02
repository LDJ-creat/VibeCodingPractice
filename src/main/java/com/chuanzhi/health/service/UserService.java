package com.chuanzhi.health.service;

import com.chuanzhi.health.dao.UserDao;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.util.PasswordUtil;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果登录成功，返回 User 对象；否则返回 null
     */
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 用户注册
     *
     * @param user 要注册的用户信息
     * @return 如果注册成功，返回 true；如果用户已存在，返回 false
     */
    public boolean register(User user) {
        if (userDao.findByUsername(user.getUsername()) != null) {
            // 用户已存在
            return false;
        }
        // 加密密码
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userDao.add(user);
        return true;
    }
}