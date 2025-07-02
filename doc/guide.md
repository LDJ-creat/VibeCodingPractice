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
