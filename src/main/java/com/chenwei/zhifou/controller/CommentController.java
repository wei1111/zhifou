package com.chenwei.zhifou.controller;

import com.chenwei.zhifou.entity.Comment;
import com.chenwei.zhifou.entity.HostHolder;
import com.chenwei.zhifou.enums.ResultEnum;
import com.chenwei.zhifou.service.CommentService;
import com.chenwei.zhifou.service.QuestionService;
import com.chenwei.zhifou.service.SensitiveService;
import com.chenwei.zhifou.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 15:29
 * @Description:
 */
@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensitiveService sensitiveService;

    /**
     * @param questionId
     * @param content    给问题添加评论
     */
    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {

        try {
            Comment comment = new Comment();
            comment.setContent(sensitiveService.filter(content));
            comment.setEntityId(questionId);
            comment.setCreatedDate(new Date());
            comment.setEntityType(ResultEnum.ENTITY_QUESTION.getCode());
            comment.setStatus(0);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(ResultEnum.ANONYMOUS_USERID.getCode());
            }
            commentService.addComment(comment);

            //增加question下面的count的数目
//            questionService
//                    .updateCommentCount(questionId, questionService.getById(questionId)
//                            .getCommentCount() + 1);

            int count = commentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(), count);
            // 怎么异步化
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }

        return "redirect:/question/" + String.valueOf(questionId);
    }

}
