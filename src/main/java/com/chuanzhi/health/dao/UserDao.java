package com.chuanzhi.health.dao;

import java.util.List;

import com.chuanzhi.health.model.User;

public interface UserDao {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象，如果不存在则返回 null
     */
    User findByUsername(String username);

    /**
     * 添加新用户
     * @param user 用户对象
     */
    void add(User user);

    /**
     * 根据用户ID查找用户角色
     * @param userId 用户ID
     * @return 角色名称列表
     */
    List<String> findUserRoles(Integer userId);
}