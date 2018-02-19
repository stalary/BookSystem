package com.stalary.book.interceptor;

import com.stalary.book.annotation.LoginRequired;
import com.stalary.book.data.ResultEnum;
import com.stalary.book.data.entity.Ticket;
import com.stalary.book.data.entity.User;
import com.stalary.book.exception.MyException;
import com.stalary.book.handle.UserContextHolder;
import com.stalary.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
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
        // 调用需要登录的接口时进行判断
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        boolean isLoginRequired = isAnnotationPresent(method, LoginRequired.class);
        if (isLoginRequired) {
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