package com.chenwei.zhifou.dao;

import com.chenwei.zhifou.ZhifouApplication;
import com.chenwei.zhifou.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZhifouApplication.class)
public class QuestionDAOTest {

    @Autowired
    QuestionDAO questionDAO;

//    private String title;
//    private String content;
//    private Date createdDate;
//    private int userId;
//    private int commentCount;

    @Test
    @Rollback(false)
    public void addQuestion() {
        Question question = new Question("十万个为什么为什么？", "为什么java的语法麻烦？", new Date(), 1, 1);
        int i = questionDAO.addQuestion(question);
        System.out.println(i);
    }

    @Test
    public void selectLatestQuestions() {
    }
}