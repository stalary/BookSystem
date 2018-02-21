package com.stalary.book.data;

import lombok.Getter;

/**
 * @author Stalary
 * @description
 * @date 2018/01/04
 */
public enum ResultEnum {
    UNKNOW_ERROR(-1000, "未知错误！"),
    NEED_LOGIN(-1, "未登录！"),
    REPEAT_REGISTER(-2, "该用户已注册！"),
    USERNAME_ERROR(-3, "用户名错误！"),
    PASSWORD_ERROR(-4, "密码错误！"),
    EMAIL_ERROR(-5, "邮箱错误！"),
    NOT_REGISTER(-6, "未注册"),
    BOOK_FORMAT_ERROR(-7, "图书格式错误"),
    BOOK_DELETE_ERROR(-8, "非图书上传者无权限删除该图书"),
    SUCCESS(0, "成功");

    @Getter
    private Integer code;

    @Getter
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
