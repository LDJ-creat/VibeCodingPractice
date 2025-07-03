注意：roocode的任务执行可能中断，比如完成一个子任务后中断不开启新的子任务—解决方案：关闭当前任务后再次打开，记得保持orchitect模式，然后点击恢复任务

# 1.需求分析内容的准备与数据库表结构的设计

由于老师已经提供，那我就直接贴在这里不自己写了

```jsx
要求学生根据老师给定的选题和需求描述，1人1组，独自完成需求分析，程序模块设计以及程序的编写、调试和测试。要求使用Java语言基于Java Swing框架实现一个桌面应用程序，数据库可选用MySQL。
项目选题为：健康管理系统
主要需求描述如下：
1、登录注册功能：实现用户登录功能；实现用户注册功能；拓展：结合物联网技术，实现刷卡登录；读取dll文件。
2、检查项管理：查询所有检查项:分页查询、编号搜索查询；创建检查项:血红蛋白、白细胞等；删除检查项目；修改检查项信息。
3、检查组管理：检查组查询: 关联查询三种机制、搜索查询；创建检查组: 勾选多个检查项形成检查组；删除检查组：检查组信息修改。
4、预约与跟踪：预约体检；体检方式选择；体检结果分析；用户病史对比与跟踪。

```

数据库表结构设计内容见PPT文件

[基于Java Swing的传智健康系统.pptx](attachment:74684b6d-71a7-42ef-bddd-090c1c3e1af9:基于Java_Swing的传智健康系统.pptx)

## 2.提取，整合，优化已有的内容并生成规范的开发文档和Agent开发指导手册

1. 将PPT文件提供给Gemini让其分析文件内容并总结项目的技术选型，功能需求以及表结构设计（包含每个表的名称，表内含有的字段，字段的类型，表之间的关系），生成初步的开发文档

```jsx
根据我上传给你的文件，帮我总结出该项目的需求分析，所需的数据库结构以及表结构的设计，功能要求，技术选型并汇总到单个文档
```

1. 向Gemini补充需求描述（即内容1中老师提供的内容）更新开发文档的内容，确保开发文档详尽，准确
2. 构架Agent开发指导手册：向Gemini提供编写好的开发文档 ，并要求他按照完整规范的项目开发流程分阶段设计提示词，要求agent可以根据提示词自主完成该项目的开发并保证项目结构和代码编写的规范性以及项目的健壮性

```jsx
--以上述项目为例，一个完整规范的项目开发流程是怎么样的，同时我应该如何制定要求和设计提示词来实现agent可以独立完成该项目的开发并保证项目结构和代码编写的规范性以及项目的健壮性
```

1. 对Gemini生成的内容进行微调

## 3.在IDEA中新建项目，并把Gemini生成的开发文档以及指导手册以markdown文件格式存放到doc文件夹下

## 4.打开RooCode并切换到orchestrator模式，将开发文档以及指导手册提供给roocode并要求其严格按照文档中的内容和指定的规范进行任务规划和任务拆分，接下来roocode会在不同的模式下切换来完成项目的开发

## requirement.md

```jsx
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

```

## guide.md

```jsx
### **AI Agent 指导手册：传智健康管理系统 (V1.0) **

#### **文档目标**
本手册旨在提供一套完整、清晰、可执行的指令（Prompts），用于指导AI Agent分阶段、分模块地完成“传智健康管理系统”的开发与测试。您的角色是项目经理，负责下达指令、审查代码和控制项目流程。

#### **核心技术栈与规范**
*   **语言**: Java 17
*   **构建工具**: Apache Maven
*   **数据库**: MySQL
*   **数据访问**: 纯 JDBC
*   **项目结构**: 标准Maven分层结构 (`com.chuanzhi.health.model`, `dao`, `service`, `view`, `util`, `test`)
*   **编码规范**:
    *   **资源管理**: 严格使用 `try-with-resou.invokeLater()` 更新UI。
*   **测试理念**: 在开发业务代码的同时编写单元测试，并在模块开发完成后进行集成与系统测试。
*   **开发过程跟踪和管理**：请你每完成一个任务则将该任务的总结记录到doc\process.md文件中

---

### **第一阶段：项目初始化与核心基础架构**

**目标**：搭建项目骨架，配置环境，并创建可复用的核心工具类。

**Prompt 1.1: 创建项目 `pom.xml` 文件 (已更新)**
> **任务**: 为“传智健康管理系统”生成 `pom.xml` 配置文件。
> **要求**:
> 1.  GroupID: `com.chuanzhi.health`, ArtifactID: `health-management-system`, 版本: `1.0.0`
> 2.  **属性**: Java 17, UTF-8编码。
> 3.  **依赖 (Dependencies)**:
>     *   `mysql:mysql-connector-java`
>     *   `com.zaxxer:HikariCP`
>     *   `org.mindrot:jbcrypt`
>     *   **新增测试依赖**:
>         *   `org.junit.jupiter:junit-jupiter-api` (scope: test)
>         *   `org.junit.jupiter:junit-jupiter-engine` (scope: test)
>         *   `org.mockito:mockito-core` (scope: test)
>         *   `org.mockito:mockito-junit-jupiter` (scope: test)
> 4.  **插件 (Plugins)**: `maven-compiler-plugin`, `maven-surefire-plugin` (用于运行测试), `maven-assembly-plugin`。
> **输出**: 提供完整的 `pom.xml` 文件内容。

**Prompt 1.2: 生成数据库初始化SQL脚本**
> **任务**: 根据项目开发文档中的数据表设计，为系统生成完整的MySQL DDL初始化脚本。
> **上下文**: [在此处粘贴您文档中的所有8个表格定义]
> **要求**:
> 1.  使用 `CREATE TABLE IF NOT EXISTS` 语句。
> 2.  正确设置主键、外键、自增 (`AUTO_INCREMENT`) 及非空 (`NOT NULL`) 约束。
> 3.  所有表和字段使用 `utf8mb4` 字符集和 `utf8mb4_unicode_ci` 排序规则。
> 4.  在脚本末尾，插入初始化数据：
>     *   在 `role` 表中插入 '管理员' (role_id=1) 和 '普通用户' (role_id=2)。
>     *   在 `users` 表中插入一个管理员账户 (username: 'admin', password: '123456' - 我们稍后会用程序加密它)。
>     *   在 `user_role` 表中将 'admin' 用户与 '管理员' 角色关联。
> **输出**: 提供一个名为 `init.sql` 的完整SQL文件内容。

**Prompt 1.3: 创建数据库连接池工具类**
> **任务**: 创建一个基于HikariCP的数据库连接池工具类 `DBUtil`。
> **要求**:
> 1.  包名: `com.chuanzhi.health.util`
> 2.  类名: `DBUtil`
> 3.  使用 **单例模式** 确保整个应用只有一个 `HikariDataSource` 实例。
> 4.  从一个配置文件（如 `db.properties`）读取数据库URL、用户名和密码。如果简化，也可以直接硬编码在类中作为静态常量。
> 5.  提供一个 `public static Connection getConnection() throws SQLException` 方法，用于从连接池获取连接。
> **输出**: 提供完整的 `DBUtil.java` 代码。

**Prompt 1.4: 创建密码加密工具类**
> **任务**: 创建一个密码处理工具类 `PasswordUtil`。
> **要求**:
> 1.  包名: `com.chuanzhi.health.util`
> 2.  类名: `PasswordUtil`
> 3.  集成 `jBCrypt` 库。
> 4.  提供两个静态方法:
>     *   `public static String hashPassword(String plainTextPassword)`: 将明文密码哈希化。
>     *   `public static boolean checkPassword(String plainTextPassword, String hashedPassword)`: 校验明文密码与哈希值是否匹配。
> **输出**: 提供完整的 `PasswordUtil.java` 代码。

---

### **第二阶段：分模块功能开发与单元测试**

**核心思想**: 每个业务功能单元的开发都伴随着对应的单元测试。遵循 **代码实现 -> 单元测试** 的微循环。

#### **模块一：登录与注册模块**

**Prompt 2.1.1: 创建User模型和DAO (含测试)**
> **任务**: 创建 `User.java`, `UserDao` 接口, `UserDaoImpl` 实现，并为其编写单元测试。
> **要求 (代码)**:
> 1.  **Model**: `com.chuanzhi.health.model.User`。
> 2.  **DAO接口**: `com.chuanzhi.health.dao.UserDao`，定义 `findByUsername`, `add`, `findUserRoles` 方法。
> 3.  **DAO实现**: `com.chuanzhi.health.dao.impl.UserDaoImpl`，使用纯JDBC实现。
> **要求 (测试)**:
> 4.  创建 `src/test/java/com/chuanzhi/health/dao/UserDaoImplTest.java`。
> 5.  使用 JUnit 5 编写测试方法，对 `add` 和 `findByUsername` 进行测试。**注意**: 此测试会与开发数据库交互，请确保测试前后数据的清洁（例如，测试后删除添加的用户）。
> **输出**: 提供 `User.java`, `UserDao.java`, `UserDaoImpl.java` 和 `UserDaoImplTest.java` 的完整代码。

**Prompt 2.1.2: 创建UserService (含测试)**
> **任务**: 创建 `UserService` 并为其编写单元测试。
> **要求 (代码)**:
> 1.  创建 `com.chuanzhi.health.service.UserService`。
> 2.  实现 `login` 和 `register` 方法，内部调用 `UserDao`。
> **要求 (测试)**:
> 3.  创建 `src/test/java/com/chuanzhi/health/service/UserServiceTest.java`。
> 4.  **使用 Mockito 模拟 `UserDao` 接口**。
> 5.  测试 `login` 成功的场景（当DAO返回用户且密码匹配时）。
> 6.  测试 `login` 失败的场景（用户不存在或密码错误）。
> 7.  测试 `register` 成功的场景（用户名不存在）。
> 8.  测试 `register` 失败的场景（用户名已存在）。
> **输出**: 提供 `UserService.java` 和 `UserServiceTest.java` 的完整代码。

**Prompt 2.1.3: 创建登录和注册界面**
> **任务**: 创建 `LoginFrame.java` 和 `RegisterFrame.java`。
> **要求**:
> 1.  **LoginFrame (`JFrame`)**: 包含用户名、密码输入框和“登录”、“注册”按钮。点击“登录”调用 `UserService.login`，成功后关闭登录窗口并打开主窗口 `MainFrame`，失败则用 `JOptionPane` 提示错误。点击“注册”则打开 `RegisterFrame`。
> 2.  **RegisterFrame (`JDialog`)**: 包含注册所需的所有信息输入框，以及“确认注册”和“取消”按钮。点击“确认注册”时，进行基本的前端校验（如密码两次输入是否一致），然后调用 `UserService.register`，并根据结果给出提示。
> **输出**: 提供 `LoginFrame.java` 和 `RegisterFrame.java` 的完整Swing代码。

---
#### **模块二：检查项管理模块 (管理员功能)**

**Prompt 2.2.1: 创建Checkitem后端代码 (含测试)**
> **任务**: 创建 `Checkitem` 相关的 Model, DAO, Service，并编写对应的单元测试。
> **要求 (代码)**:
> 1.  Model: `Checkitem.java`
> 2.  DAO: `CheckitemDao` 接口和 `CheckitemDaoImpl` 实现，包含CRUD和条件查询。
> 3.  Service: `CheckitemService` 封装业务逻辑。
> **要求 (测试)**:
> 4.  **DAO测试**: 创建 `CheckitemDaoImplTest.java`，测试其核心CRUD方法。
> 5.  **Service测试**: 创建 `CheckitemServiceTest.java`，使用 Mockito 模拟 `CheckitemDao`，测试其业务方法。
> **输出**: 提供所有相关的 `*.java` 和 `*Test.java` 文件的代码。

**Prompt 2.2.2: 创建检查项管理界面**
> **任务**: 创建 `CheckitemPanel.java`，一个用于管理检查项的 `JPanel`。
> **要求**:
> 1.  **布局**:
>     *   顶部(`NORTH`): 搜索框(`JTextField`)、查询按钮(`JButton`)、新增按钮(`JButton`)。
>     *   中部(`CENTER`): `JScrollPane` 内嵌 `JTable`，用于显示数据。表格数据模型应使用 `DefaultTableModel`。
>     *   底部(`SOUTH`): 修改按钮(`JButton`)、删除按钮(`JButton`)。
> 2.  **交互**:
>     *   **加载**: 面板加载时，调用 `CheckitemService` 获取所有数据并填充表格。
>     *   **增/改**: 点击“新增”或“修改”按钮时，弹出一个 `JDialog` 对话框，其中包含一个表单用于填写/修改检查项信息。
>     *   **删**: 点击“删除”按钮时，获取 `JTable` 的选中行，弹出 `JOptionPane.showConfirmDialog` 确认，然后调用服务删除。
>     *   **查**: 点击“查询”，根据搜索框内容调用 `findByCondition` 并刷新表格。
> **输出**: 提供 `CheckitemPanel.java` 和用于新增/修改的 `JDialog` 类的完整Swing代码。

---
#### **模块三：检查组管理模块 (管理员功能)**

**Prompt 2.3.1: 创建Checkgroup后端代码 (含测试)**
> **任务**: 创建 `Checkgroup` 相关的 Model, DAO, Service，并编写对应的单元测试。
> **要求 (代码)**:
> 1.  Model: `Checkgroup.java`
> 2.  DAO: `CheckgroupDao` 接口和 `CheckgroupDaoImpl` 实现。**重点**: `add` 和 `update` 方法必须正确处理关联表，并支持事务。
> 3.  Service: `CheckgroupService`，负责调用DAO并管理事务。
> **要求 (测试)**:
> 4.  **Service测试**: 创建 `CheckgroupServiceTest.java`。由于涉及复杂事务，单元测试的重点是 **验证业务逻辑的正确性**。使用 Mockito 模拟 `CheckgroupDao`，验证当调用 `service.add` 方法时，`dao.add` 和 `dao.addAssociations` (假设的方法) 是否被正确地、按顺序地调用了。
> **输出**: 提供所有相关的 `*.java` 和 `*Test.java` 文件的代码。

**Prompt 2.3.2: 创建检查组管理界面**
> **任务**: 创建 `CheckgroupPanel.java` (`JPanel`)。
> **要求**:
> 1.  **主界面**: 布局与 `CheckitemPanel` 类似，`JTable` 显示检查组列表。
> 2.  **新增/修改对话框 (`JDialog`)**: 这是本模块的难点。
>     *   包含检查组基本信息（代号、名称等）的输入框。
>     *   包含一个用于选择检查项的组件。推荐使用两个 `JList` 和两个按钮（“添加 >”、“< 移除”）的穿梭框(shuttle)设计，或者一个带有复选框的 `JTable` 来展示所有可选的检查项。
>     *   提交时，收集检查组信息和所有选中的检查项ID，调用 `CheckgroupService` 的事务方法。
> **输出**: 提供 `CheckgroupPanel.java` 和其复杂的增改对话框 `JDialog` 的完整Swing代码。

---
#### **模块四：预约与跟踪模块 (用户功能)**

**Prompt 2.4.1: 创建预约与结果后端代码 (含测试)**
> **任务**: 创建 `Appointment` 和 `ExamResult` 相关的 Model, DAO, Service，并编写单元测试。
> **要求**: 与前面模块的结构一致，为 `AppointmentService` 和 `ExamResultService` (如果需要) 编写使用 Mockito 的单元测试，验证其业务逻辑。
> **输出**: 提供所有相关的 `*.java` 和 `*Test.java` 文件的代码。

**Prompt 2.4.2: 创建用户功能界面**
> **任务**: 创建 `AppointmentPanel.java` 和 `HistoryPanel.java` (`JPanel`)。
> **要求**:
> 1.  **AppointmentPanel**:
>     *   一个 `JComboBox` 让用户选择体检套餐（检查组）。数据应从 `CheckgroupService` 加载。
>     *   一个日期选择组件（若无现成库，可用 `JTextField` 并提示格式）。
>     *   一个“提交预约”按钮。
> 2.  **HistoryPanel**:
>     *   一个 `JTable` 显示用户的预约历史（预约时间、套餐、状态）。
>     *   当用户在表格中选择一行并点击“查看详情”按钮时，弹出一个 `JDialog`。
>     *   该 `JDialog` 中再用一个 `JTable` 显示该次体检的所有检查项结果，包括项目名、结果值、参考值，并用颜色或文本标出结果是否正常。
> **输出**: 提供 `AppointmentPanel.java`, `HistoryPanel.java` 及详情对话框的完整Swing代码。

---

### **第三阶段：集成、系统测试与质量保证 **

**目标**: 确保所有模块协同工作正常，应用作为一个整体符合需求，并且足够健壮。

**Prompt 3.1: 生成集成测试计划**
> **任务**: 为“传智健康管理系统”生成一份集成测试计划。
> **要求**:
> 1.  以 **Checklist（清单）** 格式输出。
> 2.  测试场景应覆盖 **层与层之间** 以及 **模块与模块之间** 的交互。
> 3.  **示例场景**:
>     *   **UI到数据库**: 测试在“检查项管理”界面点击“新增”并提交后，能否在数据库的 `checkitem` 表中查询到对应记录。
>     *   **模块间依赖**: 测试能否成功创建一个包含多个“检查项”的“检查组”，并验证 `checkgroup_checkitem` 关联表数据的正确性。
>     *   **权限流程**: 测试使用普通用户账号登录后，是否无法看到管理员专属的管理面板。
> **输出**: 提供一份Markdown格式的集成测试计划清单。

**Prompt 3.2: 生成系统测试用例**
> **任务**: 生成一份完整的、面向最终用户功能的系统测试用例脚本。
> **要求**:
> 1.  格式: `用例编号 | 模块 | 测试步骤 | 预期结果 | 实际结果 (留空)`。
> 2.  覆盖所有主要的用户故事（User Story）。
> 3.  **示例用例**:
>     *   **TC-LOGIN-01**: 登录模块 | 1. 启动应用。2. 输入正确的管理员用户名和密码。3. 点击“登录”。 | 登录成功，主窗口打开，显示管理员视图（检查项/组管理标签页）。 |
>     *   **TC-APPOINT-01**: 预约模块 | 1. 使用普通用户登录。2. 进入“我要预约”界面。3. 选择一个套餐和未来的日期。4. 点击“提交预约”。 | 系统提示“预约成功”，并在“我的历史”中能看到该条新预约记录，状态为“待检查”。 |
> **输出**: 提供一份包含至少10个核心用例的Markdown表格。

**Prompt 3.3: 生成健壮性与负面测试清单**
> **任务**: 生成一份用于测试系统鲁棒性和错误处理能力的负面测试清单。
> **要求**:
> 1.  以 **Checklist（清单）** 格式输出。
> 2.  **测试点应包括**:
>     *   **无效输入**: 在所有输入框中尝试输入空值、超长字符串、特殊字符、SQL注入代码 (`' OR '1'='1'`)。
>     *   **边界条件**: 在需要数字的地方输入字母；在日期选择时选择一个过去的时间点。
>     *   **业务逻辑冲突**: 尝试删除一个已被检查组使用的检查项；尝试注册一个已存在的用户名。
>     *   **用户操作异常**: 在表格中未选择任何行的情况下点击“修改”或“删除”按钮。
> **输出**: 提供一份详细的健壮性与负面测试清单。

---

### **第四阶段：系统集成与部署**

**目标**: 将所有功能集成到主程序中，并打包成可交付的应用。

**Prompt 4.1: 创建主窗口和程序入口**
> **任务**: 创建 `MainFrame.java` 和 `Main.java`。
> **要求**:
> 1.  **`Main.java`**: 程序入口，包含 `main` 方法。它的唯一职责是：`SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));`。
> 2.  **`MainFrame.java` (`JFrame`)**:
>     *   在构造函数中接收登录成功的 `User` 对象。
>     *   使用 `JTabbedPane` 或 `CardLayout` 作为主内容区。
>     *   **权限控制**: 根据传入 `User` 对象的角色（通过 `UserService` 查询），动态地创建并添加对应的功能面板（`JPanel`）。例如，管理员看到“检查项管理”、“检查组管理”，而普通用户看到“我要预约”、“我的历史”。
>     *   包含一个菜单栏，提供“退出登录”、“退出程序”等功能。
> **输出**: 提供 `Main.java` 和 `MainFrame.java` 的完整代码。

**Prompt 4.2: 打包项目**
> **任务**: 提供最终的Maven打包命令。
> **要求**:
> 1.  确认 `pom.xml` 中的 `maven-assembly-plugin` 配置正确无误，指定了主类 `com.chuanzhi.health.Main`。
> 2.  提供在项目根目录下执行的Maven命令，以生成可执行的 `health-management-system-1.0.0-jar-with-dependencies.jar` 文件。
> **输出**: 提供一句命令行指令。
> **示例输出**: `mvn clean package assembly:single`

```

## PPT制作：使用roocode（进行任务拆分—PPT内容生成和将内容组装成PPT）+OfficePowerPointMCPServer+draw.io(通过mermaid代码生成关系图，流程图)+Gamma(PPT制作)

提示词：

```jsx
# 任务：为我的期末大作业项目生成一份汇报PPT

你现在是一名优秀的计算机系学生代表，正在准备一场课程期末项目汇报。请根据我提供的项目资料，为我创建一份用于向 **教授、老师和同学们** 展示项目成果的PPT。

## 1. 背景信息与输入

*   **核心需求文档**：项目的主要介绍和需求，请详细阅读 `@/doc/requirement.md`。
*   **全局上下文**：为了更深入地理解项目，请扫描并分析我工作区中的所有文件，包括代码、文档、设计稿等。
*   **汇报目标**：清晰地展示项目的选题意义、技术实现、完成度以及在开发过程中的学习和收获。

## 2. 输出要求

*   **最终交付物**：调用 **Office-PowerPoint-MCP-Server工具**，直接生成一份名为 `传智健康项目汇报.pptx` 的演示文稿文件并存放在工作区项目的根目录下。
*   **内容要求**：
    *   **逻辑清晰**：汇报结构要符合学术汇报的范式，从背景、目标、实现到总结，层层递进。
    *   **重点突出**：着重展示项目的**技术难点**、你的**解决方案**以及最终的**实现效果**。
    *   **语言精炼**：内容要点化，避免大段文字，口吻要专业、谦虚、严谨。
*   **设计与视觉要求**：
    *   **风格简洁学术**：整体设计风格应简洁、美观、有学术感但不死板，尽量精美。
    *   **图表化呈现**：**必须使用图表**来辅助说明复杂的技术概念，具体要求如下：
        *   **系统架构图**：宏观展示系统的组成部分及关系。
        *   **核心业务流程图**：详细解释关键功能（如：数据处理流程）的实现逻辑。
        *   **技术栈图**：直观地展示你所运用的前后端技术。

## 3. 建议的PPT结构与内容纲要

请严格按照以下结构组织PPT，并为每一页生成精炼的标题和要点。

*   **Slide 1: 封面页**
    *   项目名称：传智教育
    *   汇报人：黎同学
    *   汇报日期：2025/07/03

*   **Slide 2: 选题意义**
    *   这个项目要解决的核心问题是什么？

*   **Slide 3: 项目目标与主要功能**
    *   本次大作业要达成的具体设计目标。
    *   为实现目标，我们设计了哪些核心功能？（可使用列表+图标展示3-5项）

*   **Slide 4: 系统整体架构 (图表)**
    *   标题：系统架构设计
    *   内容：**生成一张清晰的系统架构图**，并标注各模块（如前端、后端、数据库、API网关等）的职责。

*   **Slide 5: 技术栈选型与理由 (图表)**
    *   标题：技术栈总览
    *   内容：**生成一张技术栈图**，展示项目所用的主要技术框架和工具，并用一句话简述选择该技术的原因（如：学习曲线、社区支持、性能等）。

*   **Slide 6: 核心功能实现与难点攻克 (可含图表)**
    *   标题：核心模块实现详解
    *   内容：选取1-2个最有代表性或最复杂的模块进行展示。
    *   **（强烈建议）为其中一个难点绘制关键的业务流程图或数据流图**，清晰展示你是如何解决这个问题的。

*   **Slide 7: 系统效果展示**
    *   标题：项目成果展示
    *   内容：放置几张最具代表性的系统运行截图或GIF动图，直观展示项目成果，系统的运行截图我已存放在@/images/ 文件夹下。

*   **Slide 8: 总结与收获**
    *   **学习与收获**：通过这个项目，在技术、问题解决能力上有什么提升。
    *   **不足与展望**：客观分析项目的局限性，并提出未来可以改进的方向。

*   **Slide 9: 致谢**
    *   感谢指导老师的帮助和同学们的支持。
  
```

由于OfficePowerPointMCPServer生成的不理想，所以根据roocode在第一个子任务中生成的PPT-Content的markdown文件作为大纲，上传给Gamma进行PPT的制作，然后根据我提供的图片（通过copilot和roocode编写的mermaid代码在draw.io中生成）进行微调。

## 报告文档编写（使用roocode+OfficeServerMCP）

提供代码的介绍文件（比如我直接使用开发时的Requirement.md）然后提供编写提示词并在orchestractor模式下开启总任务，并要求将生成内容同步记录到report_content.md文件中。