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

> 什么是 ok-jwt-interceptor

项目起源的初衷是由于作者在个人项目和公司中有多处使用到 JWT 鉴权的场景，导致每个项目都写了大量 JWT 相关的重复代码，故而 JWT + 拦截器 部分抽取出公共 SDK 来供其他项目使用，也好达到统一升级的好处。

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

```xml
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

5、加密解密

```java
JwtUtils.jwtEncrypt("要加密的字符串")
JwtUtils.jwtDecrypt("要解密的字符串")
```

6、角色

ok-jwt-interceptor 内置了两个角色（admin 和 manager），可在 Controller 层方法 api 上面加上 `@RequiresAdmin` 和 `@RequiresManager` 注解。

```java
@GetMapping("/users/{id}")
@RequiresAdmin
public ServerResponse getUser(@PathVariable Integer id) {
    return userService.getUser(id);
}

@GetMapping("/users/{id}")
@RequiresManager
public ServerResponse getUser(@PathVariable Integer id) {
    return userService.getUser(id);
}
```

约定：如果要正常使用角色功能的话，则需要在登录时候配置将 `username-roleName` 一起生成 token（注：以中划线分割），ok-jwt-interceptor 会解析会对应的角色信息，举例如下：

```java
@Override
public ServerResponse login(UserLoginDTO dto) {
    String md5Password = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
    UserDO user = userRepository.findByUsernameAndPassword(dto.getUsername(), md5Password);
    if (Objects.nonNull(user)) {
        String token = user.getUsername() + "-" + user.getRole();
        return ServerResponse.ok("登录成功", JwtUtils.jwtEncrypt(token));
    }
    return ServerResponse.fail("登录失败，账号或密码错误");
}
```

7、关于返回

ok-jwt-interceptor 的所有返回信息均通过 json 返回，如：

```json
{
    "code": -2,
    "message": "用户登录失效"
}
```

8、参考接入项目

https://gitee.com/wudibo/ok-simple-cli

> 规划

- 依赖上传到 Maven 中央仓库；
- 在依赖外部最小的情况下，引入更多场景；

如果有任何想法或意见，欢迎 issue 或 pr，QQ交流群：956194623