/**
 * @(#)LoginInterceptor.java, 2017-12-26.
 * <p>
 * Copyright 2017 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.interceptor;

import com.stalary.book.data.ResultEnum;
import com.stalary.book.data.entity.Ticket;
import com.stalary.book.data.entity.User;
import com.stalary.book.exception.MyException;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * LoginInterceptor
 *
 * @author lirongqian
 * @since 26/12/2017
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (request.getRequestURI().contains("swagger")) {
            return true;
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Ticket ticket = userService.findByUser(user.getId());
            if (ticket != null) {
                if (ticket.getExpired().getTime() < System.currentTimeMillis()) {
                    throw new MyException(ResultEnum.NEED_LOGIN);
                }
                UserContextHolder.set(user);
            }
        } else {
            throw new MyException(ResultEnum.NEED_LOGIN);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    private boolean isAnnotationPresent(Method method, Class<? extends Annotation> annotationClass) {
        return method.getDeclaringClass().isAnnotationPresent(annotationClass) || method.isAnnotationPresent(annotationClass);
    }
}