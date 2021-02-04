<p align="center">
    <img src="https://i.loli.net/2021/02/04/8AgwRVzMKH1C9dQ.png"/>
    <p align="center">
        一个最基础的JWT + 拦截器组件！
    </p>
    <p align="center">
        <img src="https://img.shields.io/badge/jdk-1.8-brightgreen">
        <img src="https://img.shields.io/badge/maven-3.6.1-brightgreen">
        <img src="https://img.shields.io/badge/license-MulanPSL-yellowgreen">
    </p>
</p>

---

> 什么是ok-jwt-interceptor

项目起源的初衷是由于作者在个人项目个公司中有多处使用到jwt鉴权的场景，导致每个项目都写了大量得jwt相关的重复代码，故而jwt + 拦截器部分抽取出公共组件来供其他项目使用，也好达到统一升级的好处。

> 快速开始

1、克隆项目

```
git clone https://gitee.com/ok-tool/ok-jwt-interceptor.git
```

2、执行打包命令

```
mvn install -Dmaven.test.skip=true
```

3、引入依赖

```
<dependency>
    <groupId>cn.xlbweb</groupId>
    <artifactId>ok-jwt-interceptor</artifactId>
    <version>1.0.0</version>
</dependency>
```

4、application.properties配置

```
# 开启ji组件（默认不开启）
cn.xlbweb.ji.enabled=true
# 白名单路径
cn.xlbweb.ji.exclude-uris=/swagger-ui/**,/swagger-resources/**,/v3/**,/actuator/**,/login,/logout
# 前后端交互token名称（默认JiToken）
cn.xlbweb.ji.token-name=CliToken
# token有效时间（单位分钟，默认60分钟）
cn.xlbweb.ji.token-expiration-time=60
# token失效返回码（主要为前端展示，当用户登录超时的判断，默认为-2）
cn.xlbweb.ji.token-invalid-code=-2
# token不标准返回码（token不正确或者瞎传，默认为-3）
cn.xlbweb.ji.token-nonstandard-code=-3
```