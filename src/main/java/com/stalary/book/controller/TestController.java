/**
 * @(#)TestController.java, 2018-06-11.
 * <p>
 * Copyright 2018 Stalary.
 */
package com.stalary.book.controller;

import com.stalary.book.data.ResponseMessage;
import com.stalary.book.utils.SystemUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * TestController
 *
 * @author lirongqian
 * @since 2018/06/11
 */
@ApiIgnore
@RestController
public class TestController {

    @RequestMapping("/producer")
    public ResponseMessage producer(
            String message) {
        SystemUtil.queue.offer(message);
        return ResponseMessage.successMessage();
    }


    @RequestMapping("/consumer")
    public ResponseMessage consumer() {
        String poll = SystemUtil.queue.poll();
        return ResponseMessage.successMessage(poll);
    }
}