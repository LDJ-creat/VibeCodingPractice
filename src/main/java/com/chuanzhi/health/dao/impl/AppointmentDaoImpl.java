package com.chuanzhi.health.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.chuanzhi.health.dao.AppointmentDao;
import com.chuanzhi.health.model.Appointment;
import com.chuanzhi.health.util.DBUtil;

public class AppointmentDaoImpl implements AppointmentDao {
    @Override
    public int add(Appointment appointment) {
        String sql = "INSERT INTO appointments (user_id, checkgroup_id, appointment_date, status, created_at) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, appointment.getUserId());
            pstmt.setInt(2, appointment.getSetmealId());
            pstmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            pstmt.setString(4, appointment.getStatus());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        appointment.setId(rs.getInt(1));
                    }
                }
            }
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Appointment> findByUserId(int userId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("appointment_id"));
                    appointment.setUserId(rs.getInt("user_id"));
                    appointment.setSetmealId(rs.getInt("checkgroup_id"));
                    appointment.setAppointmentDate(rs.getDate("appointment_date"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Appointment> findByUserIdWithCheckgroupName(int userId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT a.*, cg.name as checkgroup_name FROM appointments a JOIN checkgroup cg ON a.checkgroup_id = cg.gid WHERE a.user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("appointment_id"));
                    appointment.setUserId(rs.getInt("user_id"));
                    appointment.setSetmealId(rs.getInt("checkgroup_id"));
                    appointment.setAppointmentDate(rs.getDate("appointment_date"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setCreatedAt(rs.getTimestamp("created_at"));
                    appointment.setCheckgroupName(rs.getString("checkgroup_name")); // 设置套餐名称
                    list.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int updateStatus(int id, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}