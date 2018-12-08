package com.chenwei.zhifou.controller;

import com.chenwei.zhifou.entity.Question;
import com.chenwei.zhifou.service.QuestionService;
import com.chenwei.zhifou.service.UserService;
import com.chenwei.zhifou.vo.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/7 20:23
 * @Description:
 */
@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "pop", defaultValue = "0") int pop) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }

    private ArrayList<ViewObject> getQuestions(int userId, int offset, int limit) {
        ArrayList<ViewObject> vos = new ArrayList<>();
        //如果用户的id为0的化着查询所有的用户最近发布的问题，通过这样巧妙的方式，实现了一个接口多用
        List<Question> questions = questionService.getLatestQuestions(userId, 0, 10);
        for (Question question : questions) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUser(question.getUserId()));
            vos.add(viewObject);
        }
        return vos;
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "index";
    }
}
