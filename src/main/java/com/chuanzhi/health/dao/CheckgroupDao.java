package com.chuanzhi.health.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.chuanzhi.health.model.Checkgroup;

public interface CheckgroupDao {
    /**
     * 新增检查组，并返回自增主键
     * @param connection 数据库连接
     * @param checkgroup 检查组对象
     * @return 返回自增主键
     * @throws SQLException SQL异常
     */
    int add(Connection connection, Checkgroup checkgroup) throws SQLException;

    /**
     * 批量新增检查组和检查项的关联关系
     * @param connection 数据库连接
     * @param checkgroupId 检查组ID
     * @param checkitemIds 检查项ID列表
     * @throws SQLException SQL异常
     */
    void addCheckgroupCheckitem(Connection connection, Integer checkgroupId, List<Integer> checkitemIds) throws SQLException;

    /**
     * 根据ID删除检查组和检查项的关联关系
     * @param connection 数据库连接
     * @param checkgroupId 检查组ID
     * @throws SQLException SQL异常
     */
    void deleteCheckgroupCheckitem(Connection connection, Integer checkgroupId) throws SQLException;

    /**
     * 更新检查组信息
     * @param connection 数据库连接
     * @param checkgroup 检查组对象
     * @throws SQLException SQL异常
     */
    void update(Connection connection, Checkgroup checkgroup) throws SQLException;

    /**
     * 根据ID查询检查组
     * @param gid 检查组ID
     * @return 检查组对象
     */
    Checkgroup findById(int gid);

    /**
     * 查询所有检查组
     * @return 检查组列表
     */
    List<Checkgroup> findAll();

    /**
     * 根据ID删除检查组
     * @param gid 检查组ID
     */
    void deleteById(int gid);

   /**
    * 根据名称或助记码模糊查询
    * @param queryString 查询字符串
    * @return 检查组列表
    */
   List<Checkgroup> findByNameOrCode(String queryString);

   /**
    * 根据检查组ID查询关联的检查项ID列表
    * @param gid 检查组ID
    * @return 检查项ID列表
    */
   List<Integer> findCheckitemIdsByCheckgroupId(int gid);
}