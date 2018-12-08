package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.dao.QuestionDAO;
import com.chenwei.zhifou.entity.Question;
import com.chenwei.zhifou.service.QuestionService;
import com.chenwei.zhifou.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

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

    @Autowired
    SensitiveService sensitiveService;

    @Override
    public Question getById(int id) {
        return questionDAO.getById(id);
    }

    //添加问题，记住所有的用户产生的内容要过滤
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词过滤
        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    @Override
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCommentCount(int id, int count) {
        return questionDAO.updateCommentCount(id, count);
    }
}
