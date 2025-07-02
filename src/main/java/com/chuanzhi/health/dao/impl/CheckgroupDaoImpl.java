package com.chuanzhi.health.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.chuanzhi.health.dao.CheckgroupDao;
import com.chuanzhi.health.model.Checkgroup;
import com.chuanzhi.health.util.DBUtil;

public class CheckgroupDaoImpl implements CheckgroupDao {

    @Override
    public int add(Connection connection, Checkgroup checkgroup) throws SQLException {
        String sql = "INSERT INTO checkgroup(code, name, helpCode, sex, remark, attention) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, checkgroup.getCode());
            ps.setString(2, checkgroup.getName());
            ps.setString(3, checkgroup.getHelpCode());
            ps.setString(4, checkgroup.getSex());
            ps.setString(5, checkgroup.getRemark());
            ps.setString(6, checkgroup.getAttention());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    @Override
    public void addCheckgroupCheckitem(Connection connection, Integer checkgroupId, List<Integer> checkitemIds) throws SQLException {
        String sql = "INSERT INTO checkgroup_checkitem(gid, cid) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Integer checkitemId : checkitemIds) {
                ps.setInt(1, checkgroupId);
                ps.setInt(2, checkitemId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void deleteCheckgroupCheckitem(Connection connection, Integer checkgroupId) throws SQLException {
        String sql = "DELETE FROM checkgroup_checkitem WHERE gid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, checkgroupId);
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Connection connection, Checkgroup checkgroup) throws SQLException {
        String sql = "UPDATE checkgroup SET code = ?, name = ?, helpCode = ?, sex = ?, remark = ?, attention = ? WHERE gid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, checkgroup.getCode());
            ps.setString(2, checkgroup.getName());
            ps.setString(3, checkgroup.getHelpCode());
            ps.setString(4, checkgroup.getSex());
            ps.setString(5, checkgroup.getRemark());
            ps.setString(6, checkgroup.getAttention());
            ps.setInt(7, checkgroup.getGid());
            ps.executeUpdate();
        }
    }

    @Override
    public Checkgroup findById(int gid) {
        String sql = "SELECT * FROM checkgroup WHERE gid = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToCheckgroup(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Checkgroup> findAll() {
        String sql = "SELECT * FROM checkgroup";
        List<Checkgroup> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToCheckgroup(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteById(int gid) {
        // This method assumes that the transaction is handled by the service layer,
        // which should first delete from the association table.
        String sql = "DELETE FROM checkgroup WHERE gid = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Checkgroup mapRowToCheckgroup(ResultSet rs) throws SQLException {
        Checkgroup checkgroup = new Checkgroup();
        checkgroup.setGid(rs.getInt("gid"));
        checkgroup.setCode(rs.getString("code"));
        checkgroup.setName(rs.getString("name"));
        checkgroup.setHelpCode(rs.getString("helpCode"));
        checkgroup.setSex(rs.getString("sex"));
        checkgroup.setRemark(rs.getString("remark"));
        checkgroup.setAttention(rs.getString("attention"));
        return checkgroup;
    }

    @Override
    public List<Checkgroup> findByNameOrCode(String queryString) {
        StringBuilder sql = new StringBuilder("SELECT * FROM checkgroup");
        if (queryString != null && !queryString.isEmpty()) {
            sql.append(" WHERE name LIKE ? OR helpCode LIKE ?");
        }
        List<Checkgroup> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (queryString != null && !queryString.isEmpty()) {
                ps.setString(1, "%" + queryString + "%");
                ps.setString(2, "%" + queryString + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToCheckgroup(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Integer> findCheckitemIdsByCheckgroupId(int gid) {
        String sql = "SELECT cid FROM checkgroup_checkitem WHERE gid = ?";
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, gid);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("cid"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}