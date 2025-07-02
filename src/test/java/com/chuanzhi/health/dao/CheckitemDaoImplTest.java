package com.chuanzhi.health.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.chuanzhi.health.dao.impl.CheckitemDaoImpl;
import com.chuanzhi.health.model.Checkitem;
import com.chuanzhi.health.util.DBUtil;

class CheckitemDaoImplTest {

    private CheckitemDao checkitemDao;
    private Checkitem testCheckitem;

    @BeforeEach
    void setUp() throws Exception {
        checkitemDao = new CheckitemDaoImpl();
        testCheckitem = new Checkitem();
        testCheckitem.setCcode("TEST001");
        testCheckitem.setCname("测试项目");
        testCheckitem.setReferVal("N/A");
        testCheckitem.setUnit("项");
        testCheckitem.setCreateDate(new Date());
        testCheckitem.setOptionUser("tester");
        testCheckitem.setStatus("1");

        // Clean up before test
        cleanup();
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up after test
        cleanup();
    }

    private void cleanup() throws Exception {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM checkitem WHERE ccode = 'TEST001' OR ccode = 'TEST002'");
        }
    }

    @Test
    void addAndFindById() {
        // Add
        checkitemDao.add(testCheckitem);

        // Find by condition to get the generated ID
        List<Checkitem> items = checkitemDao.findByCondition("TEST001");
        assertFalse(items.isEmpty(), "Item should be found after adding");
        int newId = items.get(0).getCid();

        // Find by ID
        Checkitem found = checkitemDao.findById(newId);
        assertNotNull(found, "Should find the checkitem by ID");
        assertEquals("测试项目", found.getCname());
    }

    @Test
    void update() {
        checkitemDao.add(testCheckitem);
        List<Checkitem> items = checkitemDao.findByCondition("TEST001");
        int newId = items.get(0).getCid();

        Checkitem toUpdate = checkitemDao.findById(newId);
        toUpdate.setCname("更新后的测试项目");
        toUpdate.setUpdDate(new Date());
        checkitemDao.update(toUpdate);

        Checkitem updated = checkitemDao.findById(newId);
        assertEquals("更新后的测试项目", updated.getCname());
    }

    @Test
    void deleteById() {
        checkitemDao.add(testCheckitem);
        List<Checkitem> items = checkitemDao.findByCondition("TEST001");
        int newId = items.get(0).getCid();

        checkitemDao.deleteById(newId);
        Checkitem deleted = checkitemDao.findById(newId);
        assertNull(deleted, "Checkitem should be null after deletion (soft delete)");
    }

    @Test
    void findAll() {
        checkitemDao.add(testCheckitem);
        List<Checkitem> allItems = checkitemDao.findAll();
        assertTrue(allItems.stream().anyMatch(item -> "TEST001".equals(item.getCcode())));
    }

    @Test
    void findByCondition() {
        checkitemDao.add(testCheckitem);
        List<Checkitem> foundItems = checkitemDao.findByCondition("测试");
        assertEquals(1, foundItems.size());
        assertEquals("TEST001", foundItems.get(0).getCcode());

        List<Checkitem> notFoundItems = checkitemDao.findByCondition("不存在的项目");
        assertTrue(notFoundItems.isEmpty());
    }
}