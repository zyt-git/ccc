package com.fh.shop.api.interceptor;

import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        //获取方法
        Method method = handlerMethod.getMethod();
        //判断方法上是否有自定义注解
        if(!method.isAnnotationPresent(Idempotent.class)){
            return true;
        }

        String token = request.getHeader("token");
        //判断头信息是否为空
        if(StringUtils.isEmpty(token)){
            throw new GlobalException(ResponseEnum.TOKEN_HANDER_IS_NULL);
        }
        //头信息是否存在
        boolean exists = RedisUtil.exists(token);
        if(!exists){
            throw new GlobalException(ResponseEnum.TOKEN_HANDER_IS_LOSE);
        }
        //删除
        Long delete = RedisUtil.delete(token);
        //delete==1  代表第一次删除  第二次删除  0
        if(delete==0){
            throw new GlobalException(ResponseEnum.TOKEN_HANDER_IS_REPET);
        }




        return true;
    }
}
