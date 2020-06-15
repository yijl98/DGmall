package com.dragon.dgmall.manage.redissonTest;

import com.dragon.dgmall.util.RedisUtil;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DgmallManageServiceApplicationTests {

    @Autowired
    RedisUtil RedisUtil;


    @Test
    public void contextLoads() {

        Jedis jedis = RedisUtil.getJedis();

        System.out.println(jedis);
    }

}
