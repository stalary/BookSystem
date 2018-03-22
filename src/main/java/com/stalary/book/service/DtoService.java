package com.stalary.book.service;

import com.stalary.book.data.CommentEnum;
import com.stalary.book.data.dto.BookDto;
import com.stalary.book.data.dto.CommentDto;
import com.stalary.book.data.entity.Book;
import com.stalary.book.data.entity.Comment;
import com.stalary.book.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

/**
 * DtoService
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@Service
public class DtoService {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    public CommentDto getCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        int score = comment.getScore();
        commentDto.setScore(score);
        commentDto.setNickname(userService.getInfo(comment.getUserId()).getNickname());
        switch (score) {
            case 0:
                commentDto.setType(CommentEnum.NEGATIC.getType());
                break;
            case 1:
                commentDto.setType(CommentEnum.NEGATIC.getType());
                break;
            case 2:
                commentDto.setType(CommentEnum.MEDIUM.getType());
                break;
            case 3:
                commentDto.setType(CommentEnum.MEDIUM.getType());
                break;
            case 4:
                commentDto.setType(CommentEnum.GOOD.getType());
                break;
            case 5:
                commentDto.setType(CommentEnum.GOOD.getType());
                break;
            default:
                commentDto.setType("");
        }
        commentDto.setCreateTime(TimeUtil.dateToString(comment.getCreateTime()));
        return commentDto;
    }

    public BookDto getBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setBookName(book.getBookName());
        bookDto.setNickname(userService.getInfo(book.getUserId()).getNickname());
        bookDto.setCoverUrl(book.getCoverUrl());
        bookDto.setPdfUrl(book.getPdfUrl());
        bookDto.setDownloadCount(book.getDownloadCount());
        List<Comment> comments = commentService.findByBookId(book.getId());
        OptionalDouble average = comments.stream().mapToInt(Comment::getScore).average();
        if (average.isPresent()) {
            bookDto.setScore(Math.round(average.getAsDouble()));
        } else {
            bookDto.setScore(0.0);
        }
        Date date = book.getCreateTime();
        bookDto.setCreateTime(TimeUtil.dateToString(date));
        return bookDto;
    }
}