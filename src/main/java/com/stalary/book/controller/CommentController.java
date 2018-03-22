package com.stalary.book.controller;

import com.stalary.book.annotation.LoginRequired;
import com.stalary.book.data.ResponseMessage;
import com.stalary.book.data.entity.Comment;
import com.stalary.book.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommentController
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "发表评论", notes = "传入评论内容和图书id以及分数")
    @PostMapping("/comments")
    @LoginRequired
    public ResponseMessage saveComment(
            @RequestBody Comment comment) {
        boolean save = commentService.save(comment);
        if (save) {
            return ResponseMessage.successMessage("评论成功！");
        }
        return ResponseMessage.failedMessage("评论失败！请勿重复评论");
    }

}