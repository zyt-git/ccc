package com.fh.shop.api.util;

import redis.clients.jedis.Jedis;

public class RedisUtil {

    public static void set(String key,String value){
        Jedis recouce =null;
        try {
             recouce = RedisPool.getRecouce();
            recouce.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
    }


    public static void hset(String key,String value,String filed){
        Jedis recouce =null;
        try {
            recouce = RedisPool.getRecouce();
            recouce.hset(key,filed,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
    }

    public static void hdel(String key,String filed){
        Jedis recouce =null;
        try {
            recouce = RedisPool.getRecouce();
             recouce.hdel(key, filed);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
    }


    public static String hget(String key,String filed){
        Jedis recouce =null;
        String hget ="";
        try {
            recouce = RedisPool.getRecouce();
             hget = recouce.hget(key, filed);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
        return hget;
    }

    public static  void del(String key){
        Jedis recouce =null;
        try {
            recouce = RedisPool.getRecouce();
            recouce.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
    }

    public static  Long delete(String key){
        Jedis recouce =null;
        Long del =0l;
        try {
            recouce = RedisPool.getRecouce();
             del = recouce.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
        return del;
    }


    public static String get(String key){
        Jedis recouce =null;
        String result =null;
        try {
            recouce = RedisPool.getRecouce();
             result = recouce.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
        return result;
    }

    public static void setex(String key,String value,Integer second){
        Jedis recouce =null;
        try {
            recouce = RedisPool.getRecouce();
            recouce.setex(key,second,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(recouce!=null){
                recouce.close();;
            }
        }
    }

    public static void expire(String key,Integer second){
        Jedis recouce =null;
        try {
            recouce = RedisPool.getRecouce();
            recouce.expire(key,second);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(recouce!=null){
                recouce.close();;
            }
        }
    }


    public static  boolean  exists(String key){
        Jedis recouce =null;
        boolean  result =false;
        try {
            recouce = RedisPool.getRecouce();
            result = recouce.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new  RuntimeException(e.getMessage());
        }finally {
            if(recouce!=null){
                recouce.close();
            }
        }
        return result;
    }


}
