package com.chenwei.zhifou.service;

import com.chenwei.zhifou.entity.Message;

import java.util.List;

public interface MessageService {
    public int addMessage(Message message);

    public List<Message> getConversationDetail(String conversationId, int offset, int limit);

    public List<Message> getConversationList(int userId, int offset, int limit);

    public int getConvesationUnreadCount(int userId, String conversationId);
}
