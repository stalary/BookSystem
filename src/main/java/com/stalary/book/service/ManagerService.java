package com.stalary.book.service;

import com.stalary.book.data.dto.BookAndCommentDto;
import com.stalary.book.data.dto.BookDto;
import com.stalary.book.data.dto.CommentDto;
import com.stalary.book.data.entity.Book;
import com.stalary.book.handle.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * ManagerService
 *
 * @author lirongqian
 * @since 2018/02/17
 */
@Service
@Slf4j
public class ManagerService {

    private static ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DtoService dtoService;

    public void upload(MultipartFile book, String name) {
        try {
            Future<?> coverSubmit = executor.submit(() -> qiniuService.uploadCover(book));
            Future<?> bookSubmit = executor.submit(() -> qiniuService.uploadBook(book));
            Book newBook = new Book();
            if (StringUtils.isBlank(name)) {
                newBook.setBookName(bookService.getBookName(book));
            } else {
                newBook.setBookName(name);
            }
            newBook.setUserId(UserContextHolder.get().getId());
            newBook.setPdfUrl(bookSubmit.get().toString());
            newBook.setCoverUrl(coverSubmit.get().toString());
            newBook.setCreateTime(new Date());
            newBook.setUpdateTime(new Date());
            bookService.saveBook(newBook);
            log.info("upload success!");
        } catch (InterruptedException | ExecutionException e) {
            log.error("upload error!", e);
        }
    }

    public BookAndCommentDto getInfo(int id) {
        BookDto book = dtoService.getBookDto(bookService.getInfo(id));
        List<CommentDto> commentDtoList = commentService.findByBookId(id)
                .stream()
                .map(comment -> dtoService.getCommentDto(comment))
                .collect(Collectors.toList());
        return new BookAndCommentDto(book, commentDtoList);
    }

}