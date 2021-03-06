<p align="center">
    <img src="https://images.gitee.com/uploads/images/2021/0206/165428_730dc581_1152471.png"/>
    <p align="center">
        一个最基础的 JWT + 拦截器 SDK！
    </p>
    <p align="center">
        <img src="https://img.shields.io/badge/jdk-1.8-brightgreen">
        <img src="https://img.shields.io/badge/maven-3.6.1-brightgreen">
        <img src="https://img.shields.io/badge/license-MulanPSL-yellowgreen">
    </p>
</p>

---

> 什么是 ji-spring-boot-starter

ji 即 jwt interceptor的缩写，项目起源的初衷是由于作者在个人项目和公司项目中有多处使用到 JWT 鉴权的场景，导致每个项目都写了大量 JWT 相关的重复代码，为了避免重复工作，故而将 **JWT + 拦截器** 部分代码抽取出来作为公共 SDK 给其他项目使用，也好达到统一升级的好处。

> 快速开始

1、克隆项目

```
git clone https://gitee.com/ok-tool/ji-spring-boot-starter.git
```

2、执行打包命令

```
mvn install -Dmaven.test.skip=true
```

3、引入依赖

```xml
<dependency>
    <groupId>cn.xlbweb</groupId>
    <artifactId>ji-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

4、application.properties 配置

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
JwtUtils.encrypt("要加密的字符串")
JwtUtils.decrypt("要解密的字符串")
```

6、账号和角色

```java
String username = JwtUtils.getUsername(token);
String roleName = JwtUtils.getRoleName(token);
```

约定：为了正确的获取账号和角色信息，建议用户登录成功后生产的 token 串是由 **账号-角色** 组成，角色不区分大小写。

```java
JwtUtils.encrypt("zhangsan-ADMIN")
JwtUtils.encrypt("zhangsan-admin")

JwtUtils.encrypt("lisi-MANAGER")
JwtUtils.encrypt("lisi-manager")
```

7、权限

SDK 内置了两个角色（admin 和 manager），可在 Controller 层方法 api 上面加上 `@RequiresAdmin` 和 `@RequiresManager` 注解进行使用。

```java
@GetMapping("/users/{id}")
@RequiresAdmin
public ServerResponse getUser(@PathVariable Integer id) {
    return userService.getUser(id);
}
```

```java
@GetMapping("/users/{id}")
@RequiresManager
public ServerResponse getUser(@PathVariable Integer id) {
    return userService.getUser(id);
}
```

针对5、6、7点而言，为了更好的使用 SDK 的功能，以下为 JwtUtils 的常用功能和用户登录逻辑示例，方便大家参考。

```java
// 加密
String token = JwtUtils.encrypt("zhangsan-ADMIN");
System.out.println(token);
// 解密
String parseResult = JwtUtils.decrypt(token);
System.out.println(parseResult);
// 账号
String username = JwtUtils.getUsername(token);
System.out.println(username);
// 角色
String roleName = JwtUtils.getRoleName(token);
System.out.println(roleName);
```

```java
@Override
public ResponseServer login(LoginDTO dto) {
    String md5Password = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
    UserDO userDO = userRepository.findByUsernameAndPassword(dto.getUsername(), md5Password);
    if (Objects.nonNull(userDO)) {
        RoleDO roleDO = roleRepository.getOne(userDO.getRoleId());
        String userInfo = StringUtils.join(userDO.getUsername(), "-", roleDO.getRoleName());
        return ResponseServer.success("登录成功", JwtUtils.encrypt(userInfo));
    }
    return ResponseServer.error("登录失败，账号或密码错误");
}
```

8、关于返回

SDK 的所有返回信息均通过 json 形式返回，以下是几种常见返回信息。

- token 信息必须放在 header 请求头中发送到后台，否则验证不通过；

```json
{
    "code": -1,
    "message": "Token不能为空"
}
```

- token 信息过期，验证不通过；

```json
{
    "code": -2,
    "message": "用户登陆超时"
}
```

- token 信息不正确，如错误的 token 或瞎传 token，验证不通过；

```json
{
    "code": -3,
    "message": "Token验证失败"
}
```

9、参考接入项目

https://gitee.com/wudibo/ok-simple-cli

> 规划

- 依赖上传到 Maven 中央仓库；
- 在依赖外部最小的情况下，引入更多场景。

如果有任何想法或意见，欢迎 issue 或 pr，QQ 交流群：956194623