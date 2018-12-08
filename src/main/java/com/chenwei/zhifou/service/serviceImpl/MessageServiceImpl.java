package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.dao.MessageDAO;
import com.chenwei.zhifou.entity.Message;
import com.chenwei.zhifou.service.MessageService;
import com.chenwei.zhifou.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 18:07
 * @Description:
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveService sensitiveService;

    @Override
    public int addMessage(Message message) {
        message.setContent(sensitiveService.filter(message.getContent()));
        return messageDAO.addMessage(message);
    }

    @Override
    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    @Override
    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    @Override
    public int getConvesationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConvesationUnreadCount(userId, conversationId);
    }
}
