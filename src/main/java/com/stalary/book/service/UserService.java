/**
 * @(#)UserService.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.service;

import com.stalary.book.data.entity.User;
import com.stalary.book.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author lirongqian
 * @since 2018/02/09
 */
@Service
public class UserService{

    @Autowired
    private UserDao userDao;

    public void save(User user) {
        userDao.save(user);
    }
}