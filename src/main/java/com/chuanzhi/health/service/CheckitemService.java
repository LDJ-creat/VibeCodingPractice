package com.chuanzhi.health.service;

import java.util.Date;
import java.util.List;

import com.chuanzhi.health.dao.CheckitemDao;
import com.chuanzhi.health.dao.impl.CheckitemDaoImpl;
import com.chuanzhi.health.model.Checkitem;

public class CheckitemService {
    private CheckitemDao checkitemDao = new CheckitemDaoImpl();

    public void add(Checkitem checkitem) {
        checkitem.setCreateDate(new Date());
        checkitem.setStatus("1"); // 1 for active
        // In a real application, the user would be from the session
        checkitem.setOptionUser("admin"); 
        checkitemDao.add(checkitem);
    }

    public void update(Checkitem checkitem) {
        checkitem.setUpdDate(new Date());
        // In a real application, the user would be from the session
        checkitem.setOptionUser("admin");
        checkitemDao.update(checkitem);
    }

    public void deleteById(int id) {
        checkitemDao.deleteById(id);
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