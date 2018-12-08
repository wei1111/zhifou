package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.dao.QuestionDAO;
import com.chenwei.zhifou.entity.Question;
import com.chenwei.zhifou.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 19:40
 * @Description:
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionDAO questionDAO;

    @Override
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
