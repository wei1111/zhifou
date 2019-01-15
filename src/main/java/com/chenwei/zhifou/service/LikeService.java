package com.chenwei.zhifou.service;

public interface LikeService {
    public long getLikeCount(int entityType, int entityId);

    public long getDisLikeCount(int entityType, int entityId);

    public int getLikeStatus(int userId, int entityType, int entityId);

    public long like(int userId, int entityType, int entityId);

    public long disLike(int userId, int entityType, int entityId);

}
