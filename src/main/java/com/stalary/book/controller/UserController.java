package com.stalary.book.controller;

import com.stalary.book.annotation.LoginRequired;
import com.stalary.book.data.ResponseMessage;
import com.stalary.book.data.entity.User;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * UserController
 *
 * @author lirongqian
 * @since 2018/02/17
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录", notes = "只需要传入用户名和密码")
    @PostMapping("/login")
    public ResponseMessage login(
            @RequestBody User user,
            HttpServletRequest request) {
        Pair<Boolean, String> login = userService.login(user);
        if (login.getValue0()) {
            request.getSession().setAttribute("user", UserContextHolder.get());
            return ResponseMessage.successMessage(login.getValue1());
        }
        return ResponseMessage.failedMessage(login.getValue1());
    }

    @ApiOperation(value = "注册", notes = "只需要传入用户名和密码")
    @PostMapping("/register")
    public ResponseMessage register(
            @RequestBody User user,
            HttpServletRequest request) {
        Pair<Boolean, String> register = userService.register(user);
        if (register.getValue0()) {
            request.getSession().setAttribute("user", UserContextHolder.get());
            return ResponseMessage.successMessage(register.getValue1());
        }
        return ResponseMessage.failedMessage(register.getValue1());
    }

    @PutMapping("/update")
    @LoginRequired
    public ResponseMessage update(
            @RequestBody User user) {
        return ResponseMessage.successMessage();
    }
}