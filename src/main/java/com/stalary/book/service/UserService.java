/**
 * @(#)UserService.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.service;

import com.stalary.book.data.entity.User;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.mapper.UserDao;
import com.stalary.book.utils.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.javatuples.Pair;

import java.util.Date;

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

    public Pair<Boolean, String> register(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            return new Pair<>(false, "注册失败！用户名不能为空！");
        }
        if (userDao.findByName(user.getUsername()) != null) {
            return new Pair<>(false, "注册失败！该用户名已被注册！");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return new Pair<>(false, "注册失败！密码不能为空！");
        }
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        String password = user.getPassword();
        String salt = PasswordUtil.getSalt();
        user.setSalt(salt);
        user.setPassword(PasswordUtil.getPassword(password, salt));
        save(user);
        UserContextHolder.set(user);
        return new Pair<>(true, "注册成功");
    }

    public Pair<Boolean, String> login(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            return new Pair<>(false, "登录失败！用户名不能为空！");
        }
        User login = userDao.findByName(user.getUsername());
        if (login == null) {
            return new Pair<>(false, "登录失败！用户名不存在！");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return new Pair<>(false, "登录失败！密码不能为空！");
        }
        if (!login.getPassword().equals(PasswordUtil.getPassword(user.getPassword(), login.getSalt()))) {
            return new Pair<>(false, "登录失败！密码错误！");
        }
        UserContextHolder.set(login);
        return new Pair<>(true, "登录成功");
    }

    public void save(User user) {
        userDao.save(user);
    }
}