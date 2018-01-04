package com.stalary.book.exception;


import com.stalary.book.data.ResultEnum;

/**
 * @author Stalary
 * @description
 * @date 2018/01/04
 */
public class MyException extends RuntimeException {

    private Integer code;

    public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
