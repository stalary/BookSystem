package com.stalary.book.data.dto;

import com.stalary.book.data.CommentEnum;
import com.stalary.book.data.entity.Comment;
import com.stalary.book.mapper.CommentDao;
import com.stalary.book.service.BookService;
import com.stalary.book.service.UserService;
import com.stalary.book.utils.TimeUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CommentDto
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@Component
public class CommentDto {

    @Autowired
    private UserService userService;


    /**
     * 评论id
     */
    @Getter
    private int id;

    /**
     * 评论内容
     */
    @Getter
    private String content;

    /**
     * 评论分数
     */
    @Getter
    private int score = 0;

    /**
     * 评价人昵称
     */
    @Getter
    private String nickname;

    /**
     * 评价类别，好评、中评、差评
     */
    @Getter
    private String type;

    /**
     * 评论时间
     */
    @Getter
    private String createTime;

    public CommentDto getCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        int score = comment.getScore();
        this.score = score;
        this.nickname = userService.getInfo(comment.getUserId()).getNickname();
        switch (score) {
            case 0:
                this.type = CommentEnum.NEGATIC.getType();
                break;
            case 1:
                this.type = CommentEnum.NEGATIC.getType();
                break;
            case 2:
                this.type = CommentEnum.NEGATIC.getType();
                break;
            case 3:
                this.type = CommentEnum.MEDIUM.getType();
                break;
            case 4:
                this.type = CommentEnum.GOOD.getType();
                break;
            case 5:
                this.type = CommentEnum.GOOD.getType();
                break;
                default:
                    this.type = "";
        }
        this.createTime = TimeUtil.dateToString(comment.getCreateTime());
        return this;
    }
}