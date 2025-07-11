# express-delivery

## 项目简介

`express-delivery` 是一个基于 Spring Boot + Vue 实现的校园代取快递平台。项目旨在为校园用户（学生、代取员、管理员）提供便捷的快递代取、订单发布、接单、认证、提现、黑名单管理、AI助手等一站式服务。

## 主要功能

- **用户系统**：支持学生、代取员、管理员多角色登录与权限管理。
- **订单管理**：下单、接单、订单状态流转（待接单/已接单/派送中/已完成）、订单评分与反馈。
- **余额与提现**：学生可充值/消费，代取员可提现收入。
- **代取员认证**：支持上传学生证等资料，管理员审核认证。
- **黑名单机制**：管理员可拉黑违规用户，被拉黑用户可提交解封申请。
- **校园公告/资讯**：支持公告/资讯发布与管理。
- **AI助手**：集成 AI 服务，提供个性化 prompt，辅助用户操作。
- **多表关联与查询**：支持复杂的订单、用户、反馈、认证等数据的多表查询与展示。

## 安装步骤

### 1. 克隆项目

```bash
git clone https://github.com/wuweidedaxiongmao/express-delivery.git
cd express-delivery
```

### 2. 初始化数据库

- 项目提供了完整的 `sql` 脚本进行数据库初始化（详见 `record.md`）。
- 使用 MySQL，推荐 utf8mb4 字符集。

### 3. 启动后端（Spring Boot）

```bash
cd springboot
# 配置 application.yml 数据库等信息
mvn spring-boot:run
```

### 4. 启动前端（Vue）

```bash
cd vue
npm install
npm run serve
```

## 使用方法

1. **注册/登录**：支持学生和管理员注册、登录。
2. **下单/接单**：学生发布快递订单，代取员可接单，订单状态实时更新。
3. **认证/提现**：代取员上传认证资料，管理员审核。收入可随时提现。
4. **黑名单管理**：管理员可对违规用户拉黑，被拉黑用户可申请解封。
5. **AI助手**：在部分页面可体验 AI 助理功能，提高操作效率。
6. **后台管理**：管理员可管理用户、订单、公告、认证、黑名单等。

## 创新点

- 集成 AI 助手，支持自定义 prompt，提升智能化体验。
- 完善的黑名单与申诉解封流程。
- 多角色、多流程协作，贴合校园实际场景。

## 目录结构

- `springboot/` 后端 Spring Boot 源码
- `vue/` 前端 Vue 项目
- `record.md` 开发记录与数据库设计
- `README.md` 项目说明

## 贡献

欢迎 issue 和 PR！如有建议与问题，请在 GitHub 讨论区留言。

## License

MIT
