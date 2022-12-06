# 通用后台管理系统后端

自己写了一套SpringBoot后台管理系统框架，本仓库为java后端部分。（不断完善更新中）

### 技术栈

基于 Spring Boot 2.7.5 、 JWT、Spring Security5.7.4、Redis、Vue、Ant Design Pro 的前后端分离的后台管理系统， 项目采用按功能分模块的开发方式，权限控制采用 RBAC 思想，支持数据字典与数据权限管理，支持一键生成前后端代码，支持前端菜单动态路由等。

### 主要功能

- **用户管理**：提供用户的相关配置，数据库默认用户名admin，密码admin
- **角色管理**：对权限与菜单进行分配，可根据部门设置角色的数据权限
- **菜单管理**：已实现菜单动态路由，后端可配置化，支持多级菜单
- **部门管理**：可配置系统组织架构，树形表格展示
- **岗位管理**：配置各个部门的职位
- **字典管理**：可维护常用一些固定的数据，如：状态，性别等
- **系统日志**：记录用户操作日志与异常日志，方便开发人员定位排错
- 登录日志： 记录用户登录信息

### 项目结构

- `common` 为系统的公共模块，各种工具类，公共配置存在该模块
- `generator` 为系统的代码生成器模块，可以快速生成controller，service，mapper，domain
- `logging` 为系统的日志模块，其他模块如果需要记录日志需要引入该模块
- `model` 为系统的模型层，存放实体类和vo类
- `security` 为系统的security安全框架，是基于springsecurity的代码
- `server` 为系统的核心代码模块，是项目入口模块，也是最终需要打包部署的模块



