### **项目开发文档：传智健康管理系统 (V1.0)**

#### **1. 项目概述与目标**

[cite_start]本项目旨在开发一个基于 Java Swing 的桌面应用程序——**传智健康管理系统**。项目要求学生独立完成，涵盖从需求分析、模块设计、编码实现到最终测试的全过程，旨在锻炼学生独立开发软件项目的综合能力 。

[cite_start]系统核心目标是为用户提供一个便捷的数字化健康管理平台 [cite: 9][cite_start]。它不仅服务于医护人员，优化其对检查项目和患者信息的管理效率 [cite: 9][cite_start]，还为普通用户提供从预约体检、结果查询、历史数据对比到健康跟踪的一站式服务。系统致力于将传统的被动诊疗模式转变为主动的健康预防.

* **开发模式**：1人1组，独立完成。
* **技术选型**：
    * [cite_start]**开发语言**：Java [cite: 221]。
    * **框架**：Java Swing。
    * **包管理**：Maven
    * **数据访问**：JDBC
    * [cite_start]**数据库**：MySQL [cite: 222]。
* **项目选题**：健康管理系统。

#### **2. 需求分析**

为满足项目目标，系统需深度融合信息技术与医疗健康领域，满足以下核心需求：

* [cite_start]**用户界面**：提供统一、友好、操作便捷的图形用户界面（GUI） [cite: 12]。
* [cite_start]**核心管理**：实现对系统核心数据（如检查项、检查组、用户信息）的后台管理功能 [cite: 13]。
* [cite_start]**权限体系**：建立管理员与普通用户的权限分配机制，确保数据安全与操作隔离 [cite: 14]。
* **业务流程**：支持用户完成注册、登录、预约、查询、跟踪等一系列健康管理操作。
* [cite_start]**系统性能**：保证系统运行安全、稳定，且响应及时 [cite: 18][cite_start]。采用高可用架构，通过数据库事务保证数据一致性，并利用连接池优化响应速度 [cite: 19]。

#### **3. 功能需求详述**

根据项目要求，系统功能模块细化如下：

**3.1. 登录与注册模块**
* **用户注册**：提供用户注册界面，新用户可以填写信息创建账户。
* [cite_start]**用户登录**：已注册用户可通过账号密码登录系统 [cite: 15][cite_start]。系统需对用户名和密码进行验证 [cite: 322]。
* **拓展功能 (选做)**：
    * 结合物联网技术，实现刷卡登录功能。
    * 探索通过读取本地 `dll` 文件进行特定验证或功能调用。

**3.2. 检查项管理模块 (管理员功能)**
* **查询检查项**：
    * 支持分页查询，浏览所有健康检查项目。
    * [cite_start]支持按检查项编号或名称进行模糊搜索查询 [cite: 530, 601]。
* [cite_start]**创建检查项**：管理员可以添加新的检查项目，如“血红蛋白”、“白细胞”等，并定义其代号、名称、参考值、单位等信息 [cite: 16, 671]。
* [cite_start]**修改检查项**：对已存在的检查项信息进行编辑和更新 [cite: 16, 774]。
* [cite_start]**删除检查项**：从数据库中移除不再需要的检查项目 [cite: 16, 874]。

**3.3. 检查组管理模块 (管理员功能)**
* **查询检查组**：
    * [cite_start]支持搜索查询功能 [cite: 17]。
    * 实现关联查询，在查看检查组时能看到其包含的所有检查项信息。
* **创建检查组**：管理员可以通过勾选多个已存在的检查项，将它们组合成一个新的检查组（如“入职体检套餐”）。
* **修改检查组**：支持修改检查组的基本信息及其包含的检查项。
* **删除检查组**：删除不再需要的检查组。

**3.4. 预约与跟踪模块 (用户功能)**
* **预约体检**：用户可以根据需求选择检查组，并预约体检时间。
* **体检方式选择**：用户在预约时可以选择不同的体检方式或套餐。
* **体检结果分析**：用户可以查询自己的体检报告，系统需对结果进行初步分析（如指标是否在正常范围内）。
* **病史对比与跟踪**：系统需支持用户查看历次体检结果，并提供数据对比功能，以实现对健康状况的长期跟踪。

#### **4. 数据库设计以及表结构汇总**

#### 1. `users` (用户表)
[cite_start]此表用于存储所有系统用户的基本信息，包括管理员和普通用户 [cite: 25]。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`user_id`** | `INT(11)` | [cite_start]**主键**, 用户ID [cite: 31, 34, 35] |
| `username` | `VARCHAR(11)` | [cite_start]账号 [cite: 36, 40] |
| `password` | `VARCHAR(100)` | [cite_start]密码 [cite: 41, 45] |
| `uname` | `VARCHAR(20)` | [cite_start]姓名 [cite: 46, 50] |
| `tel` | `VARCHAR(11)` | [cite_start]电话 [cite: 51, 55] |
| `sex` | `VARCHAR(10)` | [cite_start]性别 [cite: 56, 60] |
| `bir` | `DATE` | [cite_start]出生日期 [cite: 61, 65] |
| `idcard` | `VARCHAR(18)` | [cite_start]身份证 [cite: 66, 70] |
| `address` | `VARCHAR(255)` | [cite_start]家庭住址 [cite: 71, 75] |
| `dep` | `VARCHAR(30)` | [cite_start]科室 [cite: 76, 80] |
| `lev` | `VARCHAR(10)` | [cite_start]职位 [cite: 81, 85] |
| `avatar` | `VARCHAR(100)` | [cite_start]头像路径 [cite: 86, 90] |

#### 2. `role` (角色表)
此表定义了系统中的用户角色（如：管理员、普通用户）。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`role_id`** | `INT` | **主键**, 角色ID |
| `role_name` | `VARCHAR(45)` | 角色名称 |

#### 3. `user_role` (用户角色关联表)
作为中间表，用于建立 `users` 表和 `role` 表之间的多对多关系。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`user_id`** | `INT(11)` | **复合主键**, **外键** 关联 `users` 表 |
| **`role_id`** | `INT` | **复合主键**, **外键** 关联 `role` 表 |

#### 4. `auth_info` (权限表)
[cite_start]此表用于存储系统的三级权限树结构（模块->列表->按钮） [cite: 92]。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`auth_id`** | `INT(11)` | [cite_start]**主键**, 权限ID [cite: 98, 101, 102] |
| `parent_id` | `INT(11)` | [cite_start]父权限ID [cite: 103, 107] |
| `auth_name` | `VARCHAR(100)` | [cite_start]权限名称 [cite: 108, 112] |
| `auth_desc` | `VARCHAR(300)` | [cite_start]权限描述 [cite: 113, 117] |
| `auth_type` | `CHAR(1)` | [cite_start]类型 (1模块/2列表/3按钮) [cite: 118, 122] |
| `auth_url` | `VARCHAR(100)` | [cite_start]访问路径 [cite: 123, 127] |
| `auth_icon` | `VARCHAR(100)` | [cite_start]图标名称 [cite: 128, 132] |
| `auth_order` | `INT(11)` | [cite_start]排序号 [cite: 133, 137] |
| `auth_state` | `CHAR(1)` | [cite_start]状态 (1启用) [cite: 138, 142] |
| `create_by` | `INT(11)` | [cite_start]创建人 [cite: 143, 147] |
| `create_time` | `DATETIME` | [cite_start]创建时间 [cite: 148, 152] |
| `update_by` | `INT(11)` | [cite_start]更新人 [cite: 153, 157] |

#### 5. `checkitem` (检查项表)
[cite_start]此表用于存储所有健康检查项目的具体标准 [cite: 159, 160]。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`cid`** | `INT(11)` | [cite_start]**主键**, 检查项ID [cite: 166, 169, 170] |
| `ccode` | `VARCHAR(20)` | [cite_start]代号 [cite: 171, 175] |
| `cname` | `VARCHAR(20)` | [cite_start]名称 [cite: 176, 180] |
| `refer_val` | `VARCHAR(50)` | [cite_start]参考值 [cite: 181, 185] |
| `unit` | `VARCHAR(20)` | [cite_start]单位 [cite: 186, 190] |
| `create_date` | `DATE` | [cite_start]创建日期 [cite: 191, 195] |
| `upd_date` | `DATE` | [cite_start]更新日期 [cite: 196, 200] |
| `delete_date` | `DATE` | [cite_start]删除日期 [cite: 201, 205] |
| `option_user`| `VARCHAR(50)` | [cite_start]操作人 [cite: 206, 210] |
| `status` | `VARCHAR(255)`| [cite_start]状态 (1启用) [cite: 211, 215] |

#### 6. `checkgroup` (检查组表)
此表用于定义由多个检查项组成的检查套餐。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`gid`** | `INT` | **主键**, 检查组ID |
| `gcode` | `VARCHAR(20)` | 代号 |
| `gname` | `VARCHAR(20)` | 名称 |
| `help_code` | `VARCHAR(20)` | 助记码 |
| `remark` | `VARCHAR(255)`| 备注 |
| `attention` | `VARCHAR(255)`| 注意事项 |

#### 7. `checkgroup_checkitem` (检查组与检查项关联表)
作为中间表，用于建立 `checkgroup` 表和 `checkitem` 表之间的多对多关系。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`gid`** | `INT` | **复合主键**, **外键** 关联 `checkgroup` 表 |
| **`cid`** | `INT(11)` | **复合主键**, **外键** 关联 `checkitem` 表 |

#### 8. `appointments` (预约表)
新增表，用于支持体检预约功能，记录用户的预约信息。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`appointment_id`** | `INT` | **主键**, 预约ID |
| `user_id` | `INT(11)` | **外键**, 关联 `users` 表，记录预约用户 |
| `checkgroup_id` | `INT` | **外键**, 关联 `checkgroup` 表，记录预约的套餐 |
| `appointment_date` | `DATETIME` | 预约的日期和时间 |
| `status` | `VARCHAR(20)` | 预约状态 (如: 待检查, 已完成, 已取消) |
| `create_time` | `DATETIME` | 记录创建时间 |

#### 9. `exam_results` (体检结果表)
新增表，用于存储用户体检完成后，每个检查项的具体结果。

| 字段名 (Field) | 类型 (Type) | 描述 (Description) |
| :--- | :--- | :--- |
| **`result_id`** | `INT` | **主键**, 结果ID |
| `appointment_id` | `INT` | **外键**, 关联 `appointments` 表 |
| `checkitem_id` | `INT(11)` | **外键**, 关联 `checkitem` 表 |
| `result_value` | `VARCHAR(100)` | 检查结果的数值或文本 |
| `is_normal` | `BOOLEAN` | 结果是否在正常范围内 |
| `result_desc` | `TEXT` | 医生对单项结果的描述或建议 |

---

### **表之间关系总结**

* **用户与角色 (多对多)**: 一个用户可以拥有多种角色，一种角色可以分配给多个用户。通过 `user_role` 中间表建立关联。
* **检查组与检查项 (多对多)**: 一个检查组包含多个检查项，一个检查项也可以属于多个不同的检查组。通过 `checkgroup_checkitem` 中间表建立关联。
* **用户与预约 (一对多)**: 一个用户可以发起多次体检预约，但一次预约只属于一个用户。通过在 `appointments` 表中设置 `user_id` 外键实现。
* **预约与检查组 (一对多)**: 一次预约通常对应一个检查组（套餐）。通过在 `appointments` 表中设置 `checkgroup_id` 外键实现。
* **预约与体检结果 (一对多)**: 一次成功的预约会产生多条体检结果，每条结果对应一个检查项。通过在 `exam_results` 表中设置 `appointment_id` 外键实现。

