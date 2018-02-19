package com.stalary.book.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Comment
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment extends BaseEntity {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论分数
     */
    private int score = 0;

    /**
     * 评价人id，根据id去获取用户的昵称
     */
    private int userId;

    /**
     * 评价图书的id
     */
    private int bookId;

}

