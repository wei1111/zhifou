package com.chenwei.zhifou.service.serviceImpl;

import com.chenwei.zhifou.ZhifouApplication;
import com.chenwei.zhifou.entity.Question;
import com.chenwei.zhifou.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhifouApplication.class)
public class QuestionServiceImplTest {

    @Autowired
    QuestionService questionService;

    @Test
    public void getLatestQuestions() {
        List<Question> latestQuestions = questionService.getLatestQuestions(2, 0, 5);
        for (int i = 0; i < latestQuestions.size(); i++) {
            System.out.println(
                    latestQuestions.get(i).getId() + " : " + latestQuestions.get(i).getContent());
        }
    }
}