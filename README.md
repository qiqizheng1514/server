# 景区评价系统 - 后端服务

## 技术栈
- Spring Boot 3.2.3
- Spring Data JPA
- MySQL 8.0+
- Maven

## 项目结构
```
server/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/server/
│   │   │       ├── config/        # 配置类
│   │   │       ├── controller/    # 控制器
│   │   │       ├── entity/        # 实体类
│   │   │       ├── repository/    # 数据访问层
│   │   │       ├── service/       # 服务层
│   │   │       └── ServerApplication.java
│   │   └── resources/
│   │       ├── application.properties  # 应用配置
│   │       ├── schema.sql             # 数据库结构
│   │       └── data.sql               # 初始数据
│   └── test/                          # 测试代码
└── pom.xml                            # Maven配置
```

## 数据库配置
- 端口：3307
- 数据库名：tourism_db
- 用户名：root
- 密码：123456

## API文档
启动服务后访问：http://localhost:8081/swagger-ui.html

## 主要功能
1. 用户管理
   - 注册
   - 登录
   - 信息修改

2. 景点评价
   - 发布评价
   - 查看评价
   - 评分

3. 收藏管理
   - 添加收藏
   - 取消收藏
   - 查看收藏列表

## 开发指南

### 环境准备
1. JDK 21
2. Maven 3.8+
3. MySQL 8.0+

### 本地开发
1. 克隆项目
2. 配置MySQL（端口3307）
3. 修改`application.properties`中的数据库配置（如需要）
4. 运行`ServerApplication.java`

### 打包部署
```bash
mvn clean package
```
生成的jar包在`target`目录下

### 配置说明
主要配置文件：`application.properties`
```properties
# 端口配置
server.port=8081

# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3307/tourism_db
spring.datasource.username=root
spring.datasource.password=123456
```

## 数据库设计

### users表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| username | VARCHAR(50) | 用户名 |
| password | VARCHAR(255) | 密码 |
| nickname | VARCHAR(50) | 昵称 |
| avatar | VARCHAR(255) | 头像 |
| create_time | TIMESTAMP | 创建时间 |
| update_time | TIMESTAMP | 更新时间 |

### reviews表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| spot_id | BIGINT | 景点ID |
| user_id | BIGINT | 用户ID |
| score | INT | 评分 |
| title | VARCHAR(100) | 标题 |
| content | TEXT | 评价内容 |
| date | TIMESTAMP | 评价时间 |

### favorites表
| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键 |
| user_id | BIGINT | 用户ID |
| spot_id | BIGINT | 景点ID |
| spot_name | VARCHAR(255) | 景点名称 |
| spot_image | VARCHAR(255) | 景点图片 |
| create_time | TIMESTAMP | 收藏时间 | 