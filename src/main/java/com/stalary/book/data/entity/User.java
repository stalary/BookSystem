/**
 * @(#)User.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * User
 *
 * @author lirongqian
 * @since 2018/02/09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    /**
     * 用户名(学号)
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 邮箱(找回密码时使用)
     */
    private String mail;

}