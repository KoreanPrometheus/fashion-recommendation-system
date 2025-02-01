package com.fastcampus.prometheus.config;

import com.fastcampus.prometheus.exception.Unauthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    /*
    AuthResolver사용 Interceptor 사용 X
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        String accessToken = request.getParameter("accessToken");
        if (accessToken != null && !accessToken.equals("")) {
            request.setAttribute("memberName", accessToken);

            return true;
        }
        throw new Unauthorized();
    }
    // 인터셉터는 계속 관리를 해주어야함 경로를 DTO로 관리하면 좋음

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
