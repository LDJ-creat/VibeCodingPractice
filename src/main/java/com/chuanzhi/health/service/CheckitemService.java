package com.chuanzhi.health.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chuanzhi.health.dao.CheckitemDao;
import com.chuanzhi.health.dao.impl.CheckitemDaoImpl;
import com.chuanzhi.health.model.Checkitem;

public class CheckitemService {
    private static final Logger logger = LoggerFactory.getLogger(CheckitemService.class);
    private CheckitemDao checkitemDao = new CheckitemDaoImpl();

    public void add(Checkitem checkitem) {
        logger.debug("开始新增检查项 - 代号: {}, 名称: {}", checkitem.getCcode(), checkitem.getCname());
        
        checkitem.setCreateDate(new Date());
        checkitem.setStatus("1"); // 1 for active
        // In a real application, the user would be from the session
        checkitem.setOptionUser("admin"); 
        
        checkitemDao.add(checkitem);
        logger.info("成功新增检查项 - 代号: {}, 名称: {}", checkitem.getCcode(), checkitem.getCname());
    }

    public void update(Checkitem checkitem) {
        logger.debug("开始更新检查项 - ID: {}, 代号: {}, 名称: {}", checkitem.getCid(), checkitem.getCcode(), checkitem.getCname());
        
        checkitem.setUpdDate(new Date());
        // In a real application, the user would be from the session
        checkitem.setOptionUser("admin");
        
        checkitemDao.update(checkitem);
        logger.info("成功更新检查项 - ID: {}, 代号: {}, 名称: {}", checkitem.getCid(), checkitem.getCcode(), checkitem.getCname());
    }

    public void deleteById(int id) {
        logger.debug("开始删除检查项 - ID: {}", id);
        
        checkitemDao.deleteById(id);
        logger.info("成功删除检查项 - ID: {}", id);
    }

    public Checkitem findById(int id) {
        return checkitemDao.findById(id);
    }

    public List<Checkitem> findAll() {
        return checkitemDao.findAll();
    }

    public List<Checkitem> findByCondition(String queryString) {
       return checkitemDao.findByCondition(queryString);
   }

   public List<Checkitem> findByCheckgroupId(Integer checkgroupId) {
       return checkitemDao.findByCheckgroupId(checkgroupId);
   }
}