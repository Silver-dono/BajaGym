package com.bajagym.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CSRFHandlerInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,Object handler,
                           ModelAndView modelAndView) throws Exception {
        String token = (String) request.getAttribute("_csrf");
        if (token==null) {
            token= UUID.randomUUID().toString();
            request.getSession().setAttribute("_csrf", token);
        }
        modelAndView.addObject("token", token);
    }
}
