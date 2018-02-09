/**
 * @(#)SessionAttritubeListener.java, 2017-12-26.
 * <p>
 * Copyright 2017 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.listener;

import com.stalary.book.data.entity.User;
import com.stalary.book.handle.UserContextHolder;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * SessionAttritubeListener
 *
 * @author lirongqian
 * @since 26/12/2017
 */
@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            UserContextHolder.set(new User());
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
            UserContextHolder.remove();
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if ("user".equals(event.getName())) {
//            User user = userService.findByTicket((String) event.getValue());
            UserContextHolder.set(new User());
        }
    }
}