package com.chenwei.zhifou.service;

import com.chenwei.zhifou.entity.Question;

import java.util.List;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 19:37
 * @Description:
 */
public interface QuestionService {

    public List<Question> getLatestQuestions(int userId, int offset, int limit);

}
