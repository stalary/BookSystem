/**
 * @(#)BookController.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.controller;

import com.stalary.book.annotation.LoginRequired;
import com.stalary.book.data.ResponseMessage;
import com.stalary.book.data.entity.Book;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.service.BookService;
import com.stalary.book.service.QiniuService;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * BookController
 *
 * @author lirongqian
 * @since 2018/02/09
 */

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private QiniuService qiniuService;

    @Autowired
    private BookService bookService;

    @LoginRequired
    @PostMapping("/book")
    public ResponseMessage uploadBook(@RequestParam("book") MultipartFile book) {
        Pair<Boolean, String> uploadBookPair = qiniuService.uploadBook(book);
        if (uploadBookPair.getValue0()) {
            String pdfUrl = uploadBookPair.getValue1();
            Pair<Boolean, String> uploadCoverPair = qiniuService.uploadCover(book);
            if (uploadCoverPair.getValue0()) {
                Book newBook = new Book();
                newBook.setBookName(bookService.getBookName(book));
                newBook.setUserId(UserContextHolder.get().getId());
                newBook.setCoverUrl(uploadCoverPair.getValue1());
                newBook.setPdfUrl(pdfUrl);
                newBook.setCreateTime(new Date());
                newBook.setUpdateTime(new Date());
                bookService.saveBook(newBook);
                return ResponseMessage.successMessage("上传图书成功");
            }
            return ResponseMessage.failedMessage(uploadCoverPair.getValue1());
        }
        return ResponseMessage.failedMessage(uploadBookPair.getValue1());
    }
}
