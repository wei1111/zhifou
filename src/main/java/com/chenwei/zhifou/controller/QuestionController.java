package com.chenwei.zhifou.controller;

import com.chenwei.zhifou.entity.Comment;
import com.chenwei.zhifou.entity.HostHolder;
import com.chenwei.zhifou.entity.Question;
import com.chenwei.zhifou.enums.ResultEnum;
import com.chenwei.zhifou.service.CommentService;
import com.chenwei.zhifou.service.QuestionService;
import com.chenwei.zhifou.service.UserService;
import com.chenwei.zhifou.util.FJsonUtil;
import com.chenwei.zhifou.vo.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 14:30
 * @Description:
 */
@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        List<Comment> commentList = commentService.getCommentsByEntity(qid, ResultEnum
                .ENTITY_QUESTION.getCode());
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            vo.set("comment", comment);
            vo.set("user", userService.getUser(comment.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments", vos);

        return "detail";
    }

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            question.setContent(content);
            question.setCreatedDate(new Date());
            question.setTitle(title);
            if (hostHolder.getUser() == null) {
                question.setUserId(ResultEnum.ANONYMOUS_USERID.getCode());
                // return WendaUtil.getJSONString(999);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if (questionService.addQuestion(question) > 0) {
                return FJsonUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }
        return FJsonUtil.getJSONString(1, "失败");
    }

}
