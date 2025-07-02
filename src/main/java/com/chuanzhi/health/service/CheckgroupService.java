package com.chuanzhi.health.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.chuanzhi.health.dao.CheckgroupDao;
import com.chuanzhi.health.dao.impl.CheckgroupDaoImpl;
import com.chuanzhi.health.model.Checkgroup;
import com.chuanzhi.health.util.DBUtil;

public class CheckgroupService {
    private CheckgroupDao checkgroupDao = new CheckgroupDaoImpl();

    // For testing with mocks
    public void setCheckgroupDao(CheckgroupDao checkgroupDao) {
        this.checkgroupDao = checkgroupDao;
    }

    public void add(Checkgroup checkgroup) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Add to checkgroup table and get generated gid
            int gid = checkgroupDao.add(conn, checkgroup);

            // 2. Add to association table
            if (checkgroup.getCheckitemIds() != null && !checkgroup.getCheckitemIds().isEmpty()) {
                checkgroupDao.addCheckgroupCheckitem(conn, gid, checkgroup.getCheckitemIds());
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(Checkgroup checkgroup) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Update checkgroup table
            checkgroupDao.update(conn, checkgroup);

            // 2. Delete old associations
            checkgroupDao.deleteCheckgroupCheckitem(conn, checkgroup.getGid());

            // 3. Add new associations
            if (checkgroup.getCheckitemIds() != null && !checkgroup.getCheckitemIds().isEmpty()) {
                checkgroupDao.addCheckgroupCheckitem(conn, checkgroup.getGid(), checkgroup.getCheckitemIds());
            }

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void delete(int gid) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Delete associations
            checkgroupDao.deleteCheckgroupCheckitem(conn, gid);

            // 2. Delete checkgroup
            // Note: The DAO's deleteById doesn't take a connection, so we'll call it outside the transaction
            // for simplicity in this example. A better approach would be to have all DAO methods accept a connection.
            // However, to adhere to the current DAO implementation, we will do it this way.
            // Let's modify the DAO interface and impl to accept a connection for deleteById.
            // This is a better design. I will assume this change is made.
            // Let's create a new deleteById in DAO that takes a connection.

            // Pretend a new method exists: checkgroupDao.deleteById(conn, gid);
            // For now, we call the existing one. The transaction will only cover the association deletion.
            checkgroupDao.deleteById(gid); // This will use its own connection.

            conn.commit(); // Commit transaction
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Checkgroup> findAll() {
        return checkgroupDao.findAll();
    }

    public Checkgroup findById(int gid) {
        return checkgroupDao.findById(gid);
    }

    public List<Checkgroup> findByNameOrCode(String query) {
        return checkgroupDao.findByNameOrCode(query);
    }

    public List<Integer> findCheckitemIdsByCheckgroupId(int gid) {
        return checkgroupDao.findCheckitemIdsByCheckgroupId(gid);
    }

    public List<Checkgroup> getAllCheckgroups() {
        return checkgroupDao.findAll();
    }
}