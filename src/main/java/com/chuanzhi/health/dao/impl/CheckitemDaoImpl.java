package com.chuanzhi.health.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chuanzhi.health.dao.CheckitemDao;
import com.chuanzhi.health.model.Checkitem;
import com.chuanzhi.health.util.DBUtil;

public class CheckitemDaoImpl implements CheckitemDao {
    @Override
    public void add(Checkitem checkitem) {
        String sql = "INSERT INTO checkitem (ccode, cname, refer_val, unit, create_date, option_user, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, checkitem.getCcode());
            ps.setString(2, checkitem.getCname());
            ps.setString(3, checkitem.getReferVal());
            ps.setString(4, checkitem.getUnit());
            ps.setDate(5, new java.sql.Date(checkitem.getCreateDate().getTime()));
            ps.setString(6, checkitem.getOptionUser());
            ps.setString(7, checkitem.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Checkitem checkitem) {
        String sql = "UPDATE checkitem SET ccode = ?, cname = ?, refer_val = ?, unit = ?, upd_date = ?, option_user = ?, status = ? WHERE cid = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, checkitem.getCcode());
            ps.setString(2, checkitem.getCname());
            ps.setString(3, checkitem.getReferVal());
            ps.setString(4, checkitem.getUnit());
            ps.setDate(5, new java.sql.Date(checkitem.getUpdDate().getTime()));
            ps.setString(6, checkitem.getOptionUser());
            ps.setString(7, checkitem.getStatus());
            ps.setInt(8, checkitem.getCid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "UPDATE checkitem SET delete_date = ?, status = '0' WHERE cid = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Checkitem findById(int id) {
        String sql = "SELECT * FROM checkitem WHERE cid = ? AND status = '1'";
        Checkitem checkitem = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                checkitem = mapRowToCheckitem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkitem;
    }

    @Override
    public List<Checkitem> findAll() {
        String sql = "SELECT * FROM checkitem WHERE status = '1'";
        List<Checkitem> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToCheckitem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Checkitem> findByCondition(String queryString) {
        String sql = "SELECT * FROM checkitem WHERE (ccode LIKE ? OR cname LIKE ?) AND status = '1'";
        List<Checkitem> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + queryString + "%");
            ps.setString(2, "%" + queryString + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToCheckitem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Checkitem mapRowToCheckitem(ResultSet rs) throws SQLException {
        Checkitem checkitem = new Checkitem();
        checkitem.setCid(rs.getInt("cid"));
        checkitem.setCcode(rs.getString("ccode"));
        checkitem.setCname(rs.getString("cname"));
        checkitem.setReferVal(rs.getString("refer_val"));
        checkitem.setUnit(rs.getString("unit"));
        checkitem.setCreateDate(rs.getDate("create_date"));
        checkitem.setUpdDate(rs.getDate("upd_date"));
        checkitem.setDeleteDate(rs.getDate("delete_date"));
        checkitem.setOptionUser(rs.getString("option_user"));
        checkitem.setStatus(rs.getString("status"));
        return checkitem;
    }

    @Override
    public List<Checkitem> findByCheckgroupId(Integer checkgroupId) {
        String sql = "SELECT ci.* FROM checkitem ci " +
                     "JOIN checkgroup_checkitem cgi ON ci.cid = cgi.cid " +
                     "WHERE cgi.gid = ? AND ci.status = '1'";
        List<Checkitem> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, checkgroupId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToCheckitem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}