# apollo的部署和使用


<br/>
<br/>
<br/>
<br/>

# nacos的部署和使用

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

<br/>

## 项目结构

### 项目目录
![dir.png](https://github.com/wongdw/config/blob/master/nacos/img/dir.png)

### 项目config文件夹
![config-dir.png](https://github.com/wongdw/config/blob/master/nacos/img/config-dir.png)



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

### 单机启动

```shell script
/bin/bash ${nacos_home}/bin/startup.sh start -m standalone
```

<br/>


## GUI使用

[^使用版本号]: stable-1.3.0

项目部署，默认的 path为   host:8848/nacos  

账号密码为 nacos nocos



### 首页

![nacos-homePage](https://github.com/wongdw/config/blob/master/nacos/img/nacos-home-page.png)

- namespace (本图为dev) namespace类似关系型数据库的scheme，每个namespace的DataId相互独立，可以自定义添加不同的namespace适应不同的profile

- Data_Id 配置文件名（唯一）

  - 默认DataId生成规则  spring.application.name-\[spring.profiles.active\].${file-extension}

- Group 用于区分不同的微服务名称（也可设置为全局变量组）

我创建了几种比较具有代表性的配置项

`microservice-1.properties`为第一个微服务的主配置文件
`microservice-2.properties`为第二个微服务的主配置文件
`microservice-1-extend.properties`为扩展配置文件（properties格式）
`microservice-1-extendJson.json`为扩展配置文件（json格式）


### 配置页面详情

可以看到 nacos支持 text、json、xml、yaml、html、properties多种格式的配置文件

新版本的nacos支持灰度发布（beta发布）

#### properties格式的配置文件
![config-detail-properties](https://github.com/wongdw/config/blob/master/nacos/img/config-detail-properties.png)

#### json格式的配置文件
![config-detail-json](https://github.com/wongdw/config/blob/master/nacos/img/config-detail-json.png)



### 事务回滚功能

![cvs.png](https://github.com/wongdw/config/blob/master/nacos/img/cvs.png)

可以根据不同的版本号设置回滚

此外nacos还支持权限控制和多集群实现高可用


### 权限控制

![permission1.png](https://github.com/wongdw/config/blob/master/nacos/img/permission1.png)

![permission2.png](https://github.com/wongdw/config/blob/master/nacos/img/permission2.png)

可以看到naocs支持 `Role Base Access control` 和 `Resource Base Access control`，权限管理比较细粒度，在权限方面管控的比较好。


<br/>

## open-api
nacos除了提供GUI，jar包，还提供了open-api，可以使用open-api完成自动化部署。

下面列举几个比较常用的api，具体参照官方文档
```shell script
#获取配置使用GET请求，两个参数分别为dataId和group
curl -X GET "http://localhost:8848/nacos/v1/cs/configs?dataId=microservice-1.properties&group=dev"

#新增配置使用POST请求
curl -X POST "http://localhost:8848/nacos/v1/cs/configs?dataId=microservice-1.properties&group=dev"
```


<br/>


## 项目整合

### 配置文件
nacos与spring整合非常容易，这是`bootstrap.properties`

```properties
# 该文件早于 application.properties的加载，需要依赖spring-cloud

#nacos的账号密码（配置中心在本机不需要配置该项）
spring.cloud.nacos.username=nacos
spring.cloud.nacos.password=nacos

#配置中心url（可以有多个配置中心，用逗号间隔）
spring.cloud.nacos.config.server-addr=172.29.126.69:8848
#命名空间
spring.cloud.nacos.config.namespace=598cd529-6782-48c0-91b8-88dae6754b8f
#命名组
spring.cloud.nacos.config.group=microservice-1


# 配置文件优先级 共享配置文件<扩展配置文件<主配置文件

#1、主配置文件，文件名为 ${prefix}-$[profile].${file-extension}，
#在此配置中心的GUI里面，该服务的主配置文件为 microservice-1.properties，prefix 默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置
spring.cloud.nacos.config.prefix=microservice-1
spring.cloud.nacos.config.file-extension=properties


#2.1、扩展配置文件本服务有一个在GUI里有一个 “microservice-1-db.properties” 扩展配置文件
spring.cloud.nacos.config.extension-configs[0].data-id=microservice1-extend.properties
spring.cloud.nacos.config.extension-configs[0].group=microservice-1
spring.cloud.nacos.config.extension-configs[0].refresh=true


# 2.2、json格式的拓展配置文件
spring.cloud.nacos.config.extension-configs[1].data-id=microservice1-extendJson.json
spring.cloud.nacos.config.extension-configs[1].group=microservice-1
spring.cloud.nacos.config.extension-configs[1].refresh=true


#3、共享配置文件
spring.cloud.nacos.config.shared-configs[0].data-id=slf4j.properties
spring.cloud.nacos.config.shared-configs[0].group=SHARE_GROUP
spring.cloud.nacos.config.shared-configs[0].refresh=true

```


### 代码几乎不需要进行特殊的迁移（只需要添加 `@RefreshScope`注解即可 ）

```java
package priv.wdw.config.nacos.service1;

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
@RefreshScope  //加上此注解 @Value才可以自动更新（系统的配置文件不需要此注解）
public class NacosService1TestController {

    /**
     * 主配置文件 （microservice1.properties）
     */
    @Value("${service1.key:null}")
    private String val;


    /**
     * 拓展配置properties格式文件（microservice1-extend.properties）
     */
    @Value("${service1.extendKey:null}")
    private String extendKey;


    /**
     * 拓展配置json格式文件（microservice1-extend.json）
     */
    @Value("${service1.extendJsonKey:null}")
    private String extendJsonKey;


    @GetMapping("/key")
    public String getKey() {
        return System.currentTimeMillis() + "-->" + this.val;
    }


    @GetMapping("/extendKey")
    public String getExtendKey() {
        return System.currentTimeMillis() + "-->" + this.extendKey;
    }


    @GetMapping("/extendJsonKey")
    public String getExtendJsonKey(){
        return System.currentTimeMillis() + "-->" + this.extendJsonKey;
    }
}
```

**具体的deme参照项目当中的nacos-service1和nacos-service2**