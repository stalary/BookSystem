package com.stalary.book.data.dto;

import lombok.Data;

/**
 * CommentDto
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@Data
public class CommentDto {


    /**
     * 评论id
     */
    private int id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论分数
     */
    private int score = 0;

    /**
     * 评价人昵称
     */
    private String nickname;

    /**
     * 评价类别，好评、中评、差评
     */
    private String type;

    /**
     * 评论时间
     */
    private String createTime;


}