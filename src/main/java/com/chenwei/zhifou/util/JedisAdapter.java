package com.chenwei.zhifou.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chenwei.zhifou.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 20:38
 * @Description:
 */
public class JedisAdapter implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool pool;

    public static void print(int index, Object obj) {
        if (obj == null) {
            System.out.println("null");
            return;
        }
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis://192.168.43.90:6379/9");
        print(1, jedis.ping());

        Long db = jedis.getDB();
        print(2, db);

        print(3, jedis.keys("*"));
        //string
        jedis.set("hello", "world!");
        print(4, jedis.get("hello"));
        jedis.setnx("chen", "wei");
        print(5, jedis.get("chen"));
//        jedis.setex("wu", 10, "haoran");
        print(6, jedis.get("wu"));
        jedis.getSet("chen", "shiwei");
        print(7, jedis.get("chen"));
        List<String> mget = jedis.mget("chen", "wu", "hello");
        mget.forEach((str) -> {
                    if (str != null) {
                        System.out.println(str);
                    } else {
                        System.out.println("null");

                    }
                }
        );

        String listName = "list";
        String listName1 = "list1";
        jedis.del(listName);
        for (int i = 0; i < 10; ++i) {
            jedis.lpush(listName, "a" + String.valueOf(i));
            jedis.lpush(listName1, "a1" + String.valueOf(i));
        }
        List<String> brpop = jedis.brpop(0, listName, listName1);
        System.out.println(brpop);
        System.out.println("----------------->" + jedis.brpop(0, listName));

//        List<String> lrange = jedis.lrange(listName, 0, -1);
        print(8, jedis.lrange(listName, 0, -1));
        print(9, jedis.lrange(listName, 0, 20));
        print(10, jedis.llen(listName));
        print(11, jedis.lindex(listName, 3));
        print(12, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a2", "after"));
        print(13, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a2", "before"));
        print(14, jedis.lrange(listName, 0, -1));

        // hash
        String userKey = "userxx";
        jedis.hset(userKey, "name", "chenwei");
        jedis.hset(userKey, "age", "20");

        jedis.set("age", "20");
        for (int i = 0; i < 10; i++) {
            Long age = jedis.incrBy("age", 2);
            System.out.println(age);

        }
        print(15, jedis.hget(userKey, "name"));
        print(16, jedis.ttl(userKey));
        Map<String, String> stringStringMap = jedis.hgetAll(userKey);
        print(17, stringStringMap);

        print(18, jedis.hexists(userKey, "age"));
        print(19, jedis.hexists(userKey, "阿哥"));
        print(20, jedis.hkeys(userKey));
        print(21, jedis.hvals(userKey));
        print(22, jedis.hsetnx(userKey, "school", "南京邮电大学"));
        print(23, jedis.hsetnx(userKey, "age", "南京邮电大学"));
        print(24, jedis.hlen(userKey));

        // set
        String likeKey1 = "commentLike1";
        String likeKey2 = "commentLike2";

        for (int i = 0; i < 10; ++i) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }
        print(25, jedis.smembers(likeKey1));
        print(26, jedis.smembers(likeKey2));
        print(27, jedis.sunion(likeKey1, likeKey2));
        print(28, jedis.sdiff(likeKey1, likeKey2));
        print(29, jedis.sinter(likeKey1, likeKey2));
        print(25, jedis.sismember(likeKey1, "12"));
        print(26, jedis.sismember(likeKey2, "16"));
        jedis.srem(likeKey1, "5");
        print(27, jedis.smembers(likeKey1));
        jedis.smove(likeKey2, likeKey1, "25");
        print(28, jedis.smembers(likeKey1));
        print(29, jedis.scard(likeKey1));
        print(30, jedis.smembers(likeKey1));

        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 60, "Ben");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 75, "Lucy");
        jedis.zadd(rankKey, 80, "Mei");

        print(30, jedis.zcard(rankKey));
        print(31, jedis.zcount(rankKey, 61, 100));
        print(32, jedis.zscore(rankKey, "Lucy"));
        Double lucy = jedis.zincrby(rankKey, 2.77, "Lucy");
        print(33, jedis.zscore(rankKey, "Lucy"));
        print(34, jedis.zscore(rankKey, "Luc"));
        print(35, jedis.zrange(rankKey, 0, 100));
        print(36, jedis.zrange(rankKey, 0, 10));
        print(36, jedis.zrange(rankKey, 1, 3));
        print(36, jedis.zrevrange(rankKey, 1, 3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")) {
            print(37, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }

        for (String tuple : jedis.zrangeByScore(rankKey, "(60", "100")) {
            print(38, tuple);
        }

        String setKey = "zset";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");

        print(40, jedis.zlexcount(setKey, "-", "+"));
        print(41, jedis.zlexcount(setKey, "(b", "[d"));
        print(42, jedis.zlexcount(setKey, "[b", "[d"));
        jedis.zrem(setKey, "b");
        print(43, jedis.zrange(setKey, 0, 10));
        jedis.zremrangeByLex(setKey, "(c", "+");
        print(44, jedis.zrange(setKey, 0, 2));

        /*
        JedisPool pool = new JedisPool();
        for (int i = 0; i < 100; ++i) {
            Jedis j = pool.getResource();
            print(45, j.get("pv"));
            j.close();
        }*/

        User user = new User();
        user.setName("xx");
        user.setPassword("ppp");
        user.setHeadUrl("a.png");
        user.setSalt("salt");
        user.setId(1);
        print(46, JSONObject.toJSONString(user));
        print(46, JSONObject.toJSONString(user));
        jedis.set("user1", JSONObject.toJSONString(user));

        String value = jedis.get("user1");
        User user2 = JSON.parseObject(value, User.class);
        print(47, user2);
        int k = 2;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    public long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Long srem = jedis.srem(key, value);
            return srem;
        } catch (Exception e) {
            logger.info("jedis srem 发生异常：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.info("jedis scard 发生异常：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, key);
        } catch (Exception e) {
            logger.info("jedis sismember 发生异常：" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }
}
