/**
 * @(#)ResponseMessage.java, 2018-01-04.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.data;

/**
 * @author Stalary
 * @description
 * @date 2018/01/04
 */
public enum ResultEnum {
    UNKNOW_ERROR(-100, "未知错误！"),
    NEED_LOGIN(-1, "未登录！"),
    REPEAT_REGISTER(-2, "该用户已注册！"),
    USER_NOT_EXIST(-3, "不存在该用户"),
    PASSWORD_ERROR(-4, "密码错误"),
    SUCCESS(0, "成功");


    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
