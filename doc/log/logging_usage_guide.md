# 传智健康项目 - 日志系统使用指南

## 概述

本项目采用 SLF4J + Logback 的标准日志框架组合，提供了灵活的日志配置和输出方式。默认情况下，日志输出到控制台，可通过配置文件轻松切换到文件输出模式。

## 日志配置

### 配置文件位置
- **主配置文件**: `src/main/resources/logback.xml`

### 默认行为
- **输出方式**: 控制台输出
- **日志级别**: 
  - 应用层 (com.chuanzhi.health): INFO
  - 数据访问层 (com.chuanzhi.health.dao): DEBUG  
  - 根日志: WARN

### 日志格式
```
2025-07-03 10:30:15.123 [main] INFO  c.c.h.service.AppointmentService - 用户123成功创建预约，套餐ID: 1, 预约日期: 2025-07-05
```

格式说明：
- `2025-07-03 10:30:15.123`: 时间戳
- `[main]`: 线程名
- `INFO`: 日志级别
- `c.c.h.service.AppointmentService`: 简化的类名
- `用户123成功创建预约...`: 日志消息

## 切换到文件输出

### 方法一：修改配置文件
编辑 `src/main/resources/logback.xml` 文件：

1. **启用文件输出**: 找到带有注释的文件输出配置，取消注释
2. **关闭控制台输出**: 注释掉控制台输出配置

```xml
<!-- 应用层日志配置 -->
<logger name="com.chuanzhi.health" level="INFO" additivity="false">
    <!-- <appender-ref ref="CONSOLE"/> -->  <!-- 注释掉控制台输出 -->
    <appender-ref ref="ASYNC_FILE"/>       <!-- 启用文件输出 -->
</logger>
```

### 方法二：同时输出到控制台和文件
```xml
<logger name="com.chuanzhi.health" level="INFO" additivity="false">
    <appender-ref ref="CONSOLE"/>     <!-- 控制台输出 -->
    <appender-ref ref="ASYNC_FILE"/>  <!-- 文件输出 -->
</logger>
```

## 文件输出配置详解

### 日志文件位置
- **主日志文件**: `./logs/health-management.log`
- **滚动文件**: `./logs/health-management.2025-07-03.1.log`

### 滚动策略
- **按时间滚动**: 每天生成新的日志文件
- **按大小滚动**: 单个文件超过100MB时生成新文件
- **历史保留**: 保留最近30天的日志文件
- **自动清理**: 超过保留期的文件自动删除

### 文件命名规则
```
health-management.%d{yyyy-MM-dd}.%i.log
```
- `%d{yyyy-MM-dd}`: 日期格式
- `%i`: 当天文件序号（从0开始）

示例：
- `health-management.2025-07-03.0.log` - 当天第一个文件
- `health-management.2025-07-03.1.log` - 当天第二个文件

## 日志级别说明

### 级别定义
- **ERROR**: 错误信息，如数据库操作失败
- **WARN**: 警告信息，如用户尝试非法操作
- **INFO**: 一般信息，如用户登录、业务操作成功
- **DEBUG**: 调试信息，如方法参数、中间结果

### 使用建议
- **生产环境**: 建议使用 INFO 级别
- **测试环境**: 建议使用 DEBUG 级别
- **开发环境**: 可以使用 DEBUG 或 TRACE 级别

## 性能优化

### 异步日志
项目已配置异步日志 (`ASYNC_FILE`)，具有以下优势：
- **非阻塞**: 日志写入不阻塞业务线程
- **高性能**: 适合高并发场景
- **缓冲机制**: 1024个日志事件的队列缓冲

### 配置参数
```xml
<appender name="ASYNC_FILE" class="ch.qos.logback.classic.AsyncAppender">
    <discardingThreshold>0</discardingThreshold>  <!-- 不丢弃任何级别的日志 -->
    <queueSize>1024</queueSize>                   <!-- 队列大小 -->
    <appender-ref ref="FILE"/>                    <!-- 引用文件输出器 -->
</appender>
```

## 实际应用示例

### 业务操作日志
```java
// 用户登录
logger.info("用户{}登录系统，角色: {}", username, userRole);

// 预约操作
logger.info("用户{}成功创建预约，套餐ID: {}, 预约日期: {}", userId, setmealId, appointmentDate);
logger.warn("用户{}尝试创建过期预约，预约日期: {}", userId, appointmentDate);

// 数据操作
logger.debug("开始查询用户预约历史 - 用户ID: {}", userId);
logger.info("用户{}的预约历史查询完成，共{}条记录", userId, count);
```

### 异常处理日志
```java
try {
    // 业务操作
    result = performOperation();
    logger.info("操作成功完成");
} catch (SQLException e) {
    logger.error("数据库操作失败: {}", operation, e);
    throw new ServiceException("业务操作失败", e);
}
```

## 监控和维护

### 日志监控要点
1. **磁盘空间**: 定期检查 `./logs` 目录大小
2. **日志级别**: 根据环境调整合适的日志级别
3. **性能影响**: 监控日志对应用性能的影响
4. **错误频率**: 关注ERROR和WARN级别日志的频率

### 维护建议
1. **定期清理**: 虽然有自动清理机制，但建议定期检查
2. **配置调优**: 根据实际使用情况调整队列大小等参数
3. **分级输出**: 可以配置不同级别的日志输出到不同文件
4. **集成监控**: 可以集成ELK Stack等日志分析工具

## 故障排查

### 常见问题
1. **日志文件没有生成**: 检查 `./logs` 目录权限
2. **日志不输出**: 检查配置文件语法和日志级别设置
3. **性能问题**: 检查异步队列是否满了，考虑调整队列大小
4. **文件过大**: 检查滚动策略配置是否正确

### 调试步骤
1. **确认配置**: 检查 `logback.xml` 配置是否正确
2. **查看启动日志**: 应用启动时会显示 Logback 配置信息
3. **测试输出**: 在代码中添加测试日志验证配置
4. **检查权限**: 确保应用有创建和写入日志文件的权限

## 总结

通过合理配置和使用日志系统，可以大大提高应用的可观测性和可维护性。建议在开发阶段使用控制台输出便于调试，在生产环境切换到文件输出并配合日志监控工具，实现完善的运维监控体系。
