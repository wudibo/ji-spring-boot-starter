package cn.xlbweb.ji.common;

/**
 * @author: bobi
 * @date: 2020/9/8 下午10:42
 * @description: code兼容ok-util中的ResponseEnums的0,-1
 */
public enum JiEnum {

    /**
     * 用户登录超时
     */
    UN_LOGIN(-2, "用户登录超时"),

    /**
     * 用户登录超时
     */
    TOKEN_VALID_FAILED(-3, "Token校验失败");

    private final Integer code;
    private final String message;

    JiEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
