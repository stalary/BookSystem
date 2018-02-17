/**
 * @(#)BookController.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.controller;

import com.stalary.book.data.ResponseMessage;
import com.stalary.book.service.QiniuService;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping("/upload")
    public ResponseMessage uploadBook(@RequestParam("book") MultipartFile book) {
        Pair<Boolean, String> uploadBookPair = qiniuService.uploadBook(book);
        if (uploadBookPair.getValue0()) {
            String pdfUrl = uploadBookPair.getValue1();
            Pair<Boolean, String> uploadCoverPair = qiniuService.uploadCover(pdfUrl);
            if (uploadCoverPair.getValue0()) {
                return ResponseMessage.successMessage(uploadCoverPair.getValue1());
            }
            return ResponseMessage.failedMessage(uploadCoverPair.getValue1());
        }
        return ResponseMessage.failedMessage(uploadBookPair.getValue1());
    }
}
