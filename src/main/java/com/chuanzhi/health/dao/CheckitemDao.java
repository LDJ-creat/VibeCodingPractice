package com.chuanzhi.health.dao;

import java.util.List;

import com.chuanzhi.health.model.Checkitem;

public interface CheckitemDao {
    /**
     * 新增检查项
     * @param checkitem 检查项信息
     */
    void add(Checkitem checkitem);

    /**
     * 更新检查项
     * @param checkitem 检查项信息
     */
    void update(Checkitem checkitem);

    /**
     * 根据ID删除检查项
     * @param id 检查项ID
     */
    void deleteById(int id);

    /**
     * 根据ID查询检查项
     * @param id 检查项ID
     * @return 检查项
     */
    Checkitem findById(int id);

    /**
     * 查询所有检查项
     * @return 检查项列表
     */
    List<Checkitem> findAll();

    /**
     * 根据条件查询检查项
     * @param queryString 查询条件 (ccode or cname)
     * @return 检查项列表
     */
    List<Checkitem> findByCondition(String queryString);

   /**
    * 根据检查组ID查询关联的检查项列表
    * @param checkgroupId 检查组ID
    * @return 检查项列表
    */
   List<Checkitem> findByCheckgroupId(Integer checkgroupId);
}