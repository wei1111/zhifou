package com.chenwei.zhifou.service;

import com.chenwei.zhifou.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    public List<Comment> getCommentsByEntity(int entityId, int entityType);

    public int addComment(Comment comment);

    public int getCommentCount(int entityId, int entityType);

    public void deleteComment(int entityId, int entityType);
}
