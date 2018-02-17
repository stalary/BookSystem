package com.stalary.book.service;

import com.stalary.book.data.entity.Book;
import com.stalary.book.handle.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.concurrent.*;

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

    public void upload(MultipartFile book) {
        try {
            Future<?> bookSubmit = executor.submit(() -> qiniuService.uploadBook(book));
            Future<?> coverSubmit = executor.submit(() -> qiniuService.uploadCover(book));

            log.info("bookSubmit: " + bookSubmit.get());
            log.info("coverSubmit: " + coverSubmit.get());
            Book newBook = new Book();
            newBook.setBookName(bookService.getBookName(book));
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
}