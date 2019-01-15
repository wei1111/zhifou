package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.service.LikeService;
import com.chenwei.zhifou.util.JedisAdapter;
import com.chenwei.zhifou.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/9 1:54
 * @Description:
 */
public class LikeServiceImpl implements LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public long getLikeCount(int entityType, int entityId) {
        return jedisAdapter.scard(RedisKeyUtil.getLikeKey(entityType, entityId));
    }

    @Override
    public long getDisLikeCount(int entityType, int entityId) {
        return jedisAdapter.scard(RedisKeyUtil.getDisLikeKey(entityType, entityId));
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        //注意这里有喜欢有不喜欢，也有啥都么有
        boolean isLike = jedisAdapter
                .sismember(RedisKeyUtil.getLikeKey(entityType, entityId), String.valueOf(userId));
        if (isLike) {
            return 1;
        } else {
            boolean isDisLike =
                    jedisAdapter.sismember(RedisKeyUtil.getDisLikeKey(entityType, entityId),
                            String.valueOf(userId));
            return isDisLike ? -1 : 0;
        }
    }

    @Override
    public long like(int userId, int entityType, int entityId) {
        //这里注意，这里返回的是like的数目，要检查是否点了disLike,如果点了要记得删了
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public long disLike(int userId, int entityType, int entityId) {
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }
}
