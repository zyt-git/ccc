package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private RedisPool(){}
    private static JedisPool jedisPool = null;

    private static void initPool(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(1000);
        //空闲时的最小连接
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(100);
        //允许测试连接
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPool= new JedisPool(jedisPoolConfig,"192.168.118.139",7020);
    }

    //  静态块 只加载一次 在加载类的时候执行
    static {
        initPool();
    }

    public static Jedis getRecouce(){
        return jedisPool.getResource();
    }

}
