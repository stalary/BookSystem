package com.stalary.book.controller;

import com.stalary.book.annotation.LoginRequired;
import com.stalary.book.data.ResponseMessage;
import com.stalary.book.data.entity.User;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
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

    @ApiOperation(value = "登录", notes = "传入用户名(学号)和密码，如果保存密码，save传true")
    @PostMapping("/login")
    public ResponseMessage login(
            @RequestBody User user,
            @RequestParam(required = false, defaultValue = "false") boolean save,
            HttpServletRequest request) {
        if (userService.login(user, save)) {
            request.getSession().setAttribute("user", UserContextHolder.get());
            return ResponseMessage.successMessage("登录成功");
        }
        return ResponseMessage.failedMessage("登录失败");
    }

    @ApiOperation(value = "注册", notes = "传入用户名(学号)和密码和邮箱(修改密码使用)以及昵称")
    @PostMapping("/register")
    public ResponseMessage register(
            @RequestBody User user,
            HttpServletRequest request) {
        if (userService.register(user)) {
            request.getSession().setAttribute("user", UserContextHolder.get());
            return ResponseMessage.successMessage("注册成功");
        }
        return ResponseMessage.failedMessage("注册失败");
    }

    @ApiOperation(value = "修改密码", notes = "传入用户名和邮箱以及修改后的密码")
    @PutMapping("/update")
    public ResponseMessage update(
            @RequestBody User user) {
        if (userService.update(user)) {
            return ResponseMessage.successMessage("修改成功");
        }
        return ResponseMessage.failedMessage("修改失败");
    }

    @ApiOperation(value = "获取个人信息", notes = "若不传入id，则为用户获取个人信息")
    @GetMapping("/info")
    @LoginRequired
    public ResponseMessage get(
            @RequestParam(required = false, defaultValue = "0") int id) {
        if (id == 0) {
            return ResponseMessage.successMessage(UserContextHolder.get());
        }
        return ResponseMessage.successMessage(userService.getInfo(id));
    }

    @ApiOperation(value = "退出")
    @GetMapping("/logout")
    @LoginRequired
    public ResponseMessage logout(
            HttpServletRequest request) {
        userService.logout();
        request.getSession().removeAttribute("user");
        return ResponseMessage.successMessage("退出成功");
    }
}