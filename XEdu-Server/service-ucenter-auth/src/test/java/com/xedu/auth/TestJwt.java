package com.xedu.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/6 14:53.
 * @Description: jwt令牌生成和校验测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {
    /**
     * 创建jwt令牌
     */
    @Test
    public void testCreateJwt(){
        // 密钥库文件
        String keystore = "xc.keystore";
        // 密钥库密码
        String keystore_passwd = "xuechengkeystore";
        // 密钥库文件路径
        ClassPathResource classPathResource = new ClassPathResource(keystore);
        // 密钥别名
        String alias = "xckey";
        // 密钥访问密码
        String key_pass = "xuecheng";
        // 密钥工厂
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource,keystore_passwd.toCharArray());
        // 获取密钥对（公钥和私钥）
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, key_pass.toCharArray());
        // 获取私钥
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        // 获取公钥
        RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();

        // 定义 payload 信息
        Map<String, Object> token_map = new HashMap<>();
        token_map.put("name","wx");
        String token_string = JSON.toJSONString(token_map);
        // 生成jwt令牌对象
        Jwt jwt = JwtHelper.encode(token_string, new RsaSigner(aPrivate));
        // 取出jwt令牌内容
        String token = jwt.getEncoded();
        System.out.println(token);
    }

    /**
     * 资源服务使用公钥验证jwt的合法性，并对jwt解码
     */
    @Test
    public void testVerify(){
        // jwt令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoid3gifQ.Gjfu5J3lDxnSYPv1I7pB_WPmpZjYVjwjzEbhLpsROec13TQqnhSOM6kI5Wa1MFe7xS9ef4iuRkPXFiVNXuSBi9Myqw5WjBKLdnlB9DZQ510D56kRAAeOJxXMG8b_I1Q4ln_Dps1GRgUcWBZuzUPgAdUDeUh5qWt33FciOmec9EkfR9ggWyP78oM9EgsNeyETlf5hc1-G8acPFKqUY1A3NwvnwdGNSkAkvoob5oq3sZNaNyxQbiMkQw8BXEC8JzuNQAvCD3WMemDpTzlgFT5XFYGnLBEH6jTLf2PDxYe_NaAQd5pLd0ibbLofySjM0ZVtf0PI8m-S4rbAeb7LuGhupA";
        // 公钥
        String publick_key = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        // 校验jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token,new RsaVerifier(publick_key));
        // 获取jwt原始内容
        String claims = jwt.getClaims();
        // jwt令牌
        String encode = jwt.getEncoded();
        System.out.println(encode);
    }
}
