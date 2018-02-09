/**
 * @(#)UserContextHolder.java, 2017-12-26.
 * <p>
 * Copyright 2017 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.handle;

import com.stalary.book.data.entity.User;

/**
 * UserContextHolder
 *
 * @author lirongqian
 * @since 26/12/2017
 */
public class UserContextHolder {
        private static final ThreadLocal<User>  userThreadLocal = new ThreadLocal<>();

        public static  User get(){
            return userThreadLocal.get();
        }

        public static void set(User user){
            userThreadLocal.set(user);
        }

        public static void remove() {
            userThreadLocal.remove();
        }
}