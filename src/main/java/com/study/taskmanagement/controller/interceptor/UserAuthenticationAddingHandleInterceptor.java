package com.study.taskmanagement.controller.interceptor;

import com.study.taskmanagement.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationAddingHandleInterceptor
        implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView)
            throws Exception {
        final Object authPrincipal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if (!authPrincipal.equals("anonymousUser") && modelAndView != null) {
            UserDetailsImpl loggedUser = (UserDetailsImpl) authPrincipal;
            modelAndView.addObject("loggedUser", loggedUser.getUser());
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
