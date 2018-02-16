package com.stalary.book.controller;

import com.stalary.book.data.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 *
 * @author lirongqian
 * @since 2018/02/16
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/user")
    @ApiOperation(value = "注册", notes = "只需要传入用户名和密码")
    public ResponseMessage register() {

        return ResponseMessage.successMessage("注册成功");
    }

}