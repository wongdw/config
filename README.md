# nacos的项目介绍



## nacos简介

### 由alibaba公司开发的一套具有服务发现、配置中心两种主要功能的项目

gitihub https://github.com/alibaba/nacos



配置中心功能的mvn坐标（配置中心和注册中心分别有一个jar包）

```xml
 <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>2.2.1.RELEASE</version>
 </dependency>
```

[^alibaba-cloud]: 是阿里对spring-cloud框架的实现，和常用的netfix的实现有所不同



## 项目结构

![dir.png](https://github.com/wongdw/config/blob/master/nacos/img/dir.png)



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

 可以使用**Mysql数据库**，官方提供了建库脚本即：${nacos_home}/config/nocas-mysql

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

项目部署，默认的 path为   host:8848/nacos  

账号密码为 nacos nocos



## GUI首页

![nacos-homePage](https://github.com/wongdw/config/blob/master/nacos/img/nacos-homePage.png)

我创建了几种比较具有代表性的配置项

- Data Id 全局唯一的配置名称 

  - 默认DataId生成规则  spring.application.name-[spring.profiles.active].{$file-extension}

- Group 用于区分不同的微服务名称（也可设置为全局变量组）

- namespace (本图为public) 是命名空间类型数据库的scheme，每个命名空间的变量相互独立，可以自定义添加不同的namespace适应不同的profile

  



## 配置页面详情

![config-detail](https://github.com/wongdw/config/blob/master/nacos/img/config-detail.png)



可以看到 nacos支持 text、json、xml、yaml、html、properties多种格式的配置文件

新版本的nacos支持灰度发布（beta发布）



## 事务回滚功能

![cvs.png](https://github.com/wongdw/config/blob/master/nacos/img/cvs.png)

可以根据不同的版本号设置回滚

此外nacos还支持权限控制和多集群实现高可用


# 项目整合
nacos与spring整合非常容易，这是boottstarp

```properties
# 该文件早于 application.properties的加载，需要依赖spring-cloud

#nacos的账号密码（配置中心在本机不需要配置该项）
spring.cloud.nacos.username=nacos
spring.cloud.nacos.password=nacos

#配置中心url（可以有多个配置中心，用逗号间隔）
spring.cloud.nacos.config.server-addr=172.29.74.15:8848
#命名空间
spring.cloud.nacos.config.namespace=598cd529-6782-48c0-91b8-88dae6754b8f
#命名组
spring.cloud.nacos.config.group=microservice-1


# 配置文件优先级 共享配置文件<扩展配置文件<主配置文件

#1、主配置文件，文件名为 ${prefix}-$[profile].${file-extension}，
#在此配置中心的GUI里面，该服务的主配置文件为 microservice-1.properties，prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置
spring.cloud.nacos.config.prefix=microservice-1
spring.cloud.nacos.config.file-extension=properties


#2、扩展配置文件本服务有一个在GUI里有一个 “microservice-1-db.properties” 扩展配置文件
spring.cloud.nacos.config.extension-configs[0].data-id=microservice-1-db.properties
spring.cloud.nacos.config.extension-configs[0].group=microservice-1
spring.cloud.nacos.config.extension-configs[0].refresh=true


#3、共享配置文件
spring.cloud.nacos.config.shared-configs[0].data-id=slf4j.properties
spring.cloud.nacos.config.shared-configs[0].group=SHARE_GROUP
spring.cloud.nacos.config.shared-configs[0].refresh=true


```

```java
package priv.wdw.nacos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>@author wdw</p>
 * <p>@date 2020/6/20 20:58</p>
 * <p>@description </p>
 */
@RestController
@RefreshScope  //加上此注解才可以自动更新
public class NacosDemo {


    @Value(value = "${username}")
    private volatile String username;


    @Value(value = "${jdbc.url}")
    private volatile String jdbcUrl;


    @GetMapping("/printVal")
    public String printVal() {
        return "username = " + username + '\n' + "jdbc.url = " + jdbcUrl;
    }


}
```

演示结果查看子模块nacos-config