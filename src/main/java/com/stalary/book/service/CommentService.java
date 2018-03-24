package com.stalary.book.service;

import com.stalary.book.data.entity.Comment;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.mapper.CommentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * CommentService
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public List<Comment> findByBookId(int bookId) {
        return commentDao.findByBookId(bookId);
    }

    public boolean save(Comment comment) {
        Comment oldComment = commentDao.findByUserIdAndBookId(UserContextHolder.get().getId(), comment.getBookId());
        if (oldComment != null) {
            return false;
        }
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setUserId(UserContextHolder.get().getId());
        commentDao.save(comment);
        return true;
    }
}