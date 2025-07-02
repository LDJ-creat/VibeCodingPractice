package com.chuanzhi.health.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chuanzhi.health.dao.ExamResultDao;
import com.chuanzhi.health.model.ExamResult;
import com.chuanzhi.health.util.DBUtil;

public class ExamResultDaoImpl implements ExamResultDao {
    @Override
    public void addBatch(List<ExamResult> results) {
        String sql = "INSERT INTO exam_results (appointment_id, checkitem_id, result_value, result_unit, exam_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (ExamResult result : results) {
                pstmt.setInt(1, result.getAppointmentId());
                pstmt.setInt(2, result.getCheckitemId());
                pstmt.setString(3, result.getResultValue());
                pstmt.setString(4, result.getResultUnit());
                pstmt.setDate(5, new java.sql.Date(result.getExamDate().getTime()));
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider rolling back transaction
        }
    }

    @Override
    public List<ExamResult> findByAppointmentId(int appointmentId) {
        List<ExamResult> list = new ArrayList<>();
        String sql = "SELECT * FROM exam_results WHERE appointment_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ExamResult result = new ExamResult();
                    result.setId(rs.getInt("id"));
                    result.setAppointmentId(rs.getInt("appointment_id"));
                    result.setCheckitemId(rs.getInt("checkitem_id"));
                    result.setResultValue(rs.getString("result_value"));
                    result.setResultUnit(rs.getString("result_unit"));
                    result.setExamDate(rs.getDate("exam_date"));
                    list.add(result);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}