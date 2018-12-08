package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.dao.CommentDAO;
import com.chenwei.zhifou.entity.Comment;
import com.chenwei.zhifou.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 14:40
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentDAO commentDAO;

    @Override
    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId, entityType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    @Override
    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(int entityId, int entityType) {
        commentDAO.updateStatus(entityId, entityType, 1);
    }
}
