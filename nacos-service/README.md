# nacos的项目介绍



## nacos简介

### 由alibaba公司开发的一套具有服务发现、配置中心两种主要功能的项目

gitihub https://github.com/alibaba/nacos



其中关于配置中心的mvn -gav

```xml
 <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>2.2.1.RELEASE</version>
 </dependency>
```

[^alibaba-cloud]: 是阿里对spring-cloud框架的实现，和常用的netfix的实现有所不同



## 项目结构

![dir.png](https://github.com/wongdw/config/blob/master/nacos-service/img/dir.png)



## 配置文件 

### 路径和port

```properties
#*************** Spring Boot Related Configurations ***************#
### Default web context path:
server.servlet.contextPath=/nacos
### Default web server port:
server.port=8848

#*************** Network Related Configurations ***************#
### If prefer hostname over ip for Nacos server addresses in cluster.conf:
# nacos.inetutils.prefer-hostname-over-ip=false

### Specify local server's IP:
# nacos.inetutils.ip-address=
```



###  数据库部分

 可以使用**Mysql数据库**，官方提供了sql_script0在${nacos_home}/config/nocas-mysql

```properties
#*************** Config Module Related Configurations ***************#
### If user MySQL as datasource:
# spring.datasource.platform=mysql

### Count of DB:
# db.num=1

### Connect URL of DB:
# db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC
# db.user=nacos
# db.password=nacos
```



## GUI和使用

[^使用版本号]: stable-1.3.0

项目部署，默认的 path为   host:8846/nacos  

账号密码为 nacos nocos



## GUI首页

![nacos-homePage](https://github.com/wongdw/config/blob/master/nacos-service/img/nacos-homePage.png)

我创建了几种比较具有代表性的配置项

- Data Id 全局唯一的配置名称 

  - 默认DataId生成规则  spring.application.name-[spring.profiles.active].{$file-extension}

- Group 用于区分不同的微服务名称（也可设置为全局变量组）

- namespace (本图为public) 是命名空间类型数据库的scheme，每个命名空间的变量相互独立，可以自定义添加不同的namespace适应不同的profile

  



## 配置页面详情

![config-detail](https://github.com/wongdw/config/blob/master/nacos-service/img/config-detail.png)



可以看到 nacos支持 text、json、xml、yaml、html、properties多种格式的配置文件

新版本的nacos支持灰度发布（beta发布）



## 事务回滚功能

![cvs.png](https://github.com/wongdw/config/blob/master/nacos-service/img/cvs.png)

可以根据不同的版本号设置回滚



此外nacos还可已支持权限控制和多集群

demo请查看 nacos-config