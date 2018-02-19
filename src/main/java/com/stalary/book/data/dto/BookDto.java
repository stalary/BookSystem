package com.stalary.book.data.dto;

import com.stalary.book.data.entity.Book;
import com.stalary.book.data.entity.Comment;
import com.stalary.book.service.CommentService;
import com.stalary.book.service.UserService;
import com.stalary.book.utils.TimeUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

/**
 * BookDto
 *
 * @author lirongqian
 * @since 2018/02/19
 */
@Slf4j
@Component
public class BookDto {

    @Autowired

    private UserService userService;

    @Autowired
    private CommentService commentService;

    /**
     * 图书id
     */
    @Getter
    private int id;

    /**
     * 书名
     */
    @Getter
    private String bookName;

    /**
     * 上传该书的用户的昵称
     */
    @Getter
    private String nickname;

    /**
     * 封面链接
     */
    @Getter
    private String coverUrl;

    /**
     * 文件链接
     */
    @Getter
    private String pdfUrl;

    /**
     * 下载数量
     */
    @Getter
    private int downloadCount;

    /**
     * 平均分，初始为0，最高为5
     */
    @Getter
    private double score = 0.0;

    /**
     * 图书创建时间
     */
    @Getter
    private String createTime;

    public BookDto getBookDto(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.nickname = userService.getInfo(book.getUserId()).getNickname();
        this.coverUrl = book.getCoverUrl();
        this.pdfUrl = book.getPdfUrl();
        this.downloadCount = book.getDownloadCount();
        List<Comment> comments = commentService.findByBookId(book.getId());
        OptionalDouble average = comments.stream().mapToInt(Comment::getScore).average();
        if (average.isPresent()) {
            this.score = average.getAsDouble();
        } else {
            this.score = 0.0;
        }
        Date date = book.getCreateTime();
        this.createTime = TimeUtil.dateToString(date);
        return this;
    }

}