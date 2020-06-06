package com.xedu.auth;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/6 15:38.
 * @Description: Redis功能测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 测试redis的功能
     */
    @Test
    public void testRedis(){
        // 定义key
        String key = "user_token:f3289ad6-d47b-43e1-ad2a-87f3bcab7f02";
        // 定义value
        Map<String,Object> map = new HashMap<>();
        map.put("access_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5MTQ1NTM1NiwianRpIjoiZjMyODlhZDYtZDQ3Yi00M2UxLWFkMmEtODdmM2JjYWI3ZjAyIiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.EAiOuVNHpp0uQzNvJaK27sTjFW0PkHfD6WRRf3tqng_4LZkzoQNpheyIrR7RQEHkPg0N2A_otV7nHRrojrC-SgxzwivkYdrGuBKgmQfmZanwUzXJucT9Z53f2626QQ-7dpBLagCDKWOzxDs7GJKt04gGP3tPW2yyvYWG4kHJ6Zr6BWr9VWoudsiDSbO874kANbubZgv1coRi6uoALBSQOiPfz64C6gHhF4do0jKYRIf5X23PyICVkZNLKosmg5-75aBEjfh1fUNhUtHXS6aKEVfgF-RWhC4MxDdLZJo25v2k5DZFHe5m1fCzz0w8FPHzQ8RIk4HzgflsUCQUXyAepg");
        map.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOm51bGwsInVzZXJwaWMiOm51bGwsInVzZXJfbmFtZSI6Iml0Y2FzdCIsInNjb3BlIjpbImFwcCJdLCJhdGkiOiJmMzI4OWFkNi1kNDdiLTQzZTEtYWQyYS04N2YzYmNhYjdmMDIiLCJuYW1lIjpudWxsLCJ1dHlwZSI6bnVsbCwiaWQiOm51bGwsImV4cCI6MTU5MTQ1NDEwMywianRpIjoiNzcxZjQwMWItYjdmNy00OGZhLWFhMWUtYWYzMDliYTE1NWJmIiwiY2xpZW50X2lkIjoiWGNXZWJBcHAifQ.Ch6Awe60RsD9WTrG0L-zmtqsd-ZgBJFQpiVCcoyXdFIORWF9YH3ZZUe3QTsr8GYGZ1aH43dwej3yEDkpqYbDjzX9pQbe34wOBMD99dLLnM_hpglnSnnMqVDVuP-wEiQk4ShpFdpGJp0qISJmDcWNRp3W4ykl0s8o1pkcRDZNYsbW35eykFpmVPghgys_lQl9vYkpcNoPNfPs7Bps0sG5vDqGHPEk-hAPvtYCZc6WWODRynRgRBMH32VmBG57BKsRwwk1Sk0953zQpepIUbnmzb1FOP3E1v_IMiUWOjM2K5-Yskrl9Q-KlBnSe08I5Rlphc4wM-xYmQugYnl3JeocFg");
        String value = JSON.toJSONString(map);
        // 向redis中保存内容
        stringRedisTemplate.boundValueOps(key).set(value,60, TimeUnit.SECONDS);
        // 读取过期时间，已过期返回-2
        Long expire = stringRedisTemplate.getExpire(key);
        // 根据key获取value
        String s = stringRedisTemplate.opsForValue().get(key);
        System.out.println(s);
    }
}
