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
import com.stalary.book.service.ManagerService;
import com.stalary.book.service.QiniuService;
import io.swagger.annotations.ApiOperation;
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
 * @author hawk
 * @since 2018/02/09
 */

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private ManagerService managerService;

    @ApiOperation(value = "上传图书", notes = "若不传name，则默认为文件名")
    @LoginRequired
    @PostMapping("/book")
    public ResponseMessage uploadBook(
            @RequestParam("book") MultipartFile book,
            @RequestParam(required = false, defaultValue = "") String name) {
        managerService.upload(book, name);
        return ResponseMessage.successMessage("上传成功！");
    }
}
