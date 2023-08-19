# Rehabilitation-Counseling-System
Consulting system（thymeleaf，springboot2.3.5）

环境：
linux + thymeleaf + sprintboot + Spring Boot Jpa + JWT + Spring Security + Redis

#### 主要功能
##### 用户端
- 疾病信息查询
- 疾病在线咨询
- 康复计划推荐
- 器材信息浏览
- 康复文章推荐
- 咨询记录查询

##### 运营端
- 用户管理
- 管理员管理
- 康复文章管理
- 问题模板管理
- 答案模板管理
- 咨询记录管理
- 页面管理
- 网站信息监控

#### 快速上手
1. mysql数据导入
2. neo4j数据导入
3. 项目运行

### 目前的实体类别（共8类）
疾病（功能障碍）
检查
症状
科室
药物
运动
物理疗法
设备

### 目前的关系（共8类）
疾病-疾病
疾病-科室
疾病-药物
疾病-症状
疾病-检查
疾病-物理疗法
疾病-运动疗法
设备-运动疗法
