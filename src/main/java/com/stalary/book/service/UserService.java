/**
 * @(#)UserService.java, 2018-02-09.
 * <p>
 * Copyright 2018 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.service;

import com.stalary.book.data.ResultEnum;
import com.stalary.book.data.entity.Ticket;
import com.stalary.book.data.entity.User;
import com.stalary.book.exception.MyException;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.mapper.TicketDao;
import com.stalary.book.mapper.UserDao;
import com.stalary.book.utils.PasswordUtil;
import com.stalary.book.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * UserService
 *
 * @author lirongqian
 * @since 2018/02/09
 */
@Service
@Slf4j
public class UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private TicketDao ticketDao;

    public boolean register(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new MyException(ResultEnum.USERNAME_ERROR);
        }
        if (userDao.findByName(user.getUsername()) != null) {
            throw new MyException(ResultEnum.REPEAT_REGISTER);
        }
        if (StringUtils.isBlank(user.getPassword())) {
            throw new MyException(ResultEnum.PASSWORD_ERROR);
        }
        if (StringUtils.isBlank(user.getMail())) {
            throw new MyException(ResultEnum.EMAIL_ERROR);
        }
        user.setUpdateTime(new Date());
        user.setCreateTime(new Date());
        String password = user.getPassword();
        String salt = PasswordUtil.get5UUID();
        user.setSalt(salt);
        user.setPassword(PasswordUtil.getPassword(password, salt));
        save(user);

        Ticket ticket = new Ticket();
        ticket.setUserId(user.getId());
        ticket.setTicket(PasswordUtil.get10UUID());
        // 有效期默认1天
        ticket.setExpired(TimeUtil.plusDays(new Date(), 1));
        ticket.setCreateTime(new Date());
        ticket.setUpdateTime(new Date());
        ticketDao.save(ticket);
        UserContextHolder.set(user);
        return true;
    }

    public boolean login(User user, boolean save) {
        User login = userDao.findByName(user.getUsername());
        if (StringUtils.isBlank(user.getUsername()) || login == null) {
            throw new MyException(ResultEnum.USERNAME_ERROR);
        }
        if (StringUtils.isBlank(user.getPassword()) || !login.getPassword().equals(PasswordUtil.getPassword(user.getPassword(), login.getSalt()))) {
            throw new MyException(ResultEnum.PASSWORD_ERROR);
        }
        // 当点击保存密码时，延长ticket有效期
        if (save) {
            Ticket ticket = new Ticket();
            ticket.setUpdateTime(new Date());
            ticket.setExpired(TimeUtil.plusDays(new Date(), 30));
            ticket.setUserId(login.getId());
            ticket.setStatus(0);
            ticketDao.updateExpired(ticket);
        } else {
            Ticket ticket = new Ticket();
            ticket.setUpdateTime(new Date());
            ticket.setExpired(TimeUtil.plusDays(new Date(), 1));
            ticket.setUserId(login.getId());
            ticket.setStatus(0);
            ticketDao.updateExpired(ticket);
        }
        UserContextHolder.set(login);
        return true;
    }

    public void logout() {
        User user = UserContextHolder.get();
        Ticket ticket = ticketDao.findByUser(user.getId());
        ticket.setExpired(new Date());
        ticket.setUpdateTime(new Date());
        ticketDao.updateExpired(ticket);
        UserContextHolder.remove();
    }

    public boolean update(User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new MyException(ResultEnum.USERNAME_ERROR);
        }
        User update = userDao.findByName(user.getUsername());
        if (update == null) {
            throw new MyException(ResultEnum.NOT_REGISTER);
        }
        if (StringUtils.isBlank(user.getMail()) || !user.getMail().equals(update.getMail())) {
            throw new MyException(ResultEnum.EMAIL_ERROR);
        }
        update.setUpdateTime(new Date());
        update.setPassword(PasswordUtil.getPassword(user.getPassword(), user.getSalt()));
        userDao.update(update);
        return true;
    }

    public Ticket findByUser(int userId) {
        return ticketDao.findByUser(userId);
    }


    public void updateTicket(Ticket ticket) {
        ticketDao.updateExpired(ticket);
    }

    public void save(User user) {
        userDao.save(user);
    }
}