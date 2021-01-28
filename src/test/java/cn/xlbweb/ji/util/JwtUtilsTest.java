package cn.xlbweb.ji.util;


import cn.xlbweb.ji.AppTest;
import org.junit.Test;

/**
 * @author: bobi
 * @date: 2021/1/28 下午11:06
 * @description:
 */
public class JwtUtilsTest extends AppTest {

    @Test
    public void jwtEncrypt() {
        String encrypt = JwtUtils.jwtEncrypt("admin");
        System.out.println("encrypt=" + encrypt);
    }

    @Test
    public void jwtDecrypt() {
    }
}