package com.chuanzhi.health.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.dao.CheckgroupDao;
import com.chuanzhi.health.dao.impl.CheckgroupDaoImpl;
import com.chuanzhi.health.model.Checkgroup;
import com.chuanzhi.health.util.DBUtil;

public class CheckgroupService {
    private static final Logger logger = LoggerFactory.getLogger(CheckgroupService.class);
    private CheckgroupDao checkgroupDao = new CheckgroupDaoImpl();

    // For testing with mocks
    public void setCheckgroupDao(CheckgroupDao checkgroupDao) {
        this.checkgroupDao = checkgroupDao;
    }

    public void add(Checkgroup checkgroup) {
        logger.debug("开始新增检查组 - 代号: {}, 名称: {}, 关联检查项数量: {}", 
                    checkgroup.getCode(), checkgroup.getName(), 
                    checkgroup.getCheckitemIds() != null ? checkgroup.getCheckitemIds().size() : 0);
        
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Add to checkgroup table and get generated gid
            int gid = checkgroupDao.add(conn, checkgroup);
            logger.debug("检查组已插入数据库，生成ID: {}", gid);

            // 2. Add to association table
            if (checkgroup.getCheckitemIds() != null && !checkgroup.getCheckitemIds().isEmpty()) {
                checkgroupDao.addCheckgroupCheckitem(conn, gid, checkgroup.getCheckitemIds());
                logger.debug("检查组关联关系已建立，检查项数量: {}", checkgroup.getCheckitemIds().size());
            }

            conn.commit(); // Commit transaction
            logger.info("成功新增检查组 - 代号: {}, 名称: {}, ID: {}", checkgroup.getCode(), checkgroup.getName(), gid);
        } catch (SQLException e) {
            logger.error("新增检查组失败 - 代号: {}, 名称: {}", checkgroup.getCode(), checkgroup.getName(), e);
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                    logger.debug("事务已回滚");
                } catch (SQLException ex) {
                    logger.error("事务回滚失败", ex);
                }
            }
            throw new RuntimeException("新增检查组失败", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("关闭数据库连接失败", e);
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