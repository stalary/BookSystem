/**
 * @(#)LoginFilter.java, 2017-12-26.
 * <p>
 * Copyright 2017 Youdao, Inc. All rights reserved.
 * YOUDAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.stalary.book.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * LoginFilter
 *
 * @author lirongqian
 * @since 26/12/2017
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}