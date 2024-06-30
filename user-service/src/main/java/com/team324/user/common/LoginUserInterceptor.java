package com.team324.user.common;

import com.team324.common.BaseContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的用户id
        String userId = request.getHeader("authorization");

        //判断用户id是否为空
        if(userId==null||userId.equals("")){
            //为空返回false
            return false;
        }
        //不为空将用户信息储存到ThreadLocal
        BaseContext.setCurrentId(userId);

        //放行
        return true;
    }

    /**
     * 删除threadlocal中的ID
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeCurrentId();
    }
}
