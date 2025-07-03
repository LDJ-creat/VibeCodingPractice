package com.chuanzhi.health.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.dao.ExamResultDao;
import com.chuanzhi.health.model.ExamResult;
import com.chuanzhi.health.util.DBUtil;

public class ExamResultDaoImpl implements ExamResultDao {
    private static final Logger logger = LoggerFactory.getLogger(ExamResultDaoImpl.class);
    
    @Override
    public void addBatch(List<ExamResult> results) {
        logger.debug("开始批量新增体检结果，数量: {}", results.size());
        
        String sql = "INSERT INTO exam_results (appointment_id, checkitem_id, result_value, is_normal, result_desc) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (ExamResult result : results) {
                pstmt.setInt(1, result.getAppointmentId());
                pstmt.setInt(2, result.getCheckitemId());
                pstmt.setString(3, result.getResultValue());
                pstmt.setBoolean(4, result.getIsAbnormal() != null && result.getIsAbnormal() == 1);
                pstmt.setString(5, result.getResultDesc());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
            logger.info("成功批量新增体检结果，数量: {}", results.size());
        } catch (SQLException e) {
            logger.error("批量新增体检结果失败", e);
            throw new RuntimeException("批量新增体检结果失败", e);
        }
    }

    @Override
    public List<ExamResult> findByAppointmentId(int appointmentId) {
        logger.debug("查询预约ID为{}的体检结果", appointmentId);
        
        List<ExamResult> list = new ArrayList<>();
        // 关联查询checkitem表获取检查项名称和参考值
        String sql = """
            SELECT 
                er.result_id,
                er.appointment_id,
                er.checkitem_id,
                er.result_value,
                er.is_normal,
                er.result_desc,
                ci.cname as checkitem_name,
                ci.refer_val as reference_value,
                ci.unit as result_unit
            FROM exam_results er
            LEFT JOIN checkitem ci ON er.checkitem_id = ci.cid
            WHERE er.appointment_id = ? AND (ci.status = '1' OR ci.status IS NULL)
            ORDER BY er.result_id
            """;
            
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ExamResult result = new ExamResult();
                    result.setId(rs.getInt("result_id"));
                    result.setAppointmentId(rs.getInt("appointment_id"));
                    result.setCheckitemId(rs.getInt("checkitem_id"));
                    result.setResultValue(rs.getString("result_value"));
                    result.setResultUnit(rs.getString("result_unit"));
                    result.setIsAbnormal(rs.getBoolean("is_normal") ? 1 : 0);
                    result.setResultDesc(rs.getString("result_desc"));
                    
                    // 设置用于显示的字段
                    result.setCheckitemName(rs.getString("checkitem_name"));
                    result.setExamValue(rs.getString("result_value")); // 检查结果值
                    result.setReferenceValue(rs.getString("reference_value")); // 参考值
                    
                    list.add(result);
                }
            }
            logger.info("查询预约ID为{}的体检结果完成，共{}条记录", appointmentId, list.size());
        } catch (SQLException e) {
            logger.error("查询预约ID为{}的体检结果失败", appointmentId, e);
            throw new RuntimeException("查询体检结果失败", e);
        }
        return list;
    }
}