package com.chuanzhi.health.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chuanzhi.health.dao.UserDao;
import com.chuanzhi.health.model.User;
import com.chuanzhi.health.util.DBUtil;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setUname(rs.getString("uname"));
                    user.setTel(rs.getString("tel"));
                    user.setSex(rs.getString("sex"));
                    user.setBir(rs.getDate("bir"));
                    user.setIdcard(rs.getString("idcard"));
                    user.setAddress(rs.getString("address"));
                    user.setDep(rs.getString("dep"));
                    user.setLev(rs.getString("lev"));
                    user.setAvatar(rs.getString("avatar"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (username, password, uname, tel, sex, bir, idcard, address, dep, lev, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getUname());
            ps.setString(4, user.getTel());
            ps.setString(5, user.getSex());
            ps.setDate(6, user.getBir() != null ? new java.sql.Date(user.getBir().getTime()) : null);
            ps.setString(7, user.getIdcard());
            ps.setString(8, user.getAddress());
            ps.setString(9, user.getDep());
            ps.setString(10, "user");
            ps.setString(11, user.getAvatar());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> findUserRoles(Integer userId) {
        String sql = "SELECT r.role_name FROM user_role ur JOIN role r ON ur.role_id = r.role_id WHERE ur.user_id = ?";
        List<String> roles = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getString("role_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
}