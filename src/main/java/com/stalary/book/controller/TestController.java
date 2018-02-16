/**
 * @(#)TestController.java, 2018-01-04.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.controller;

import com.stalary.book.data.ResponseMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * TestController
 *
 * @author lirongqian
 * @since 2018/01/04
 */
@RequestMapping("/test")
@RestController
@ApiIgnore
public class TestController {

    @GetMapping("/get")
    public ResponseMessage testGet() {
        return ResponseMessage.successMessage("测试成功");
    }
}