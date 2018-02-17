/**
 * @(#)ResponseMessage.java, 2018-01-04.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ResponseMessage
 *
 * @author lirongqian
 * @since 2018/01/04
 */
@Data
@AllArgsConstructor
public class ResponseMessage {

    private int code;

    private String msg;

    private String description;

    private Object data;

    public ResponseMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseMessage enumError(ResultEnum resultEnum) {
        return new ResponseMessage(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static ResponseMessage error(int code, String msg) {
        return new ResponseMessage(code, msg);
    }

    public static ResponseMessage successMessage(Object data) {
        return new ResponseMessage(0, "success", null, data);
    }

    public static ResponseMessage successMessage() {
        return new ResponseMessage(0, "success", null, null);
    }

    public static ResponseMessage failedMessage(String message) {
        return new ResponseMessage(1, "failed", message, null);
    }

    public static ResponseMessage failedMessage() {
        return new ResponseMessage(1, "failed", null, null);
    }

}