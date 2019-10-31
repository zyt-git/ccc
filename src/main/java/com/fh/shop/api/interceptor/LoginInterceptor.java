package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
             {
                 response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
                 response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT");
                 response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with,Authorization,x-auth,token,content-type");
                 response.setHeader(HttpHeaders.CACHE_CONTROL,"No-Cache");

                 //过滤options请求
              String method1 = request.getMethod();
              if("OPTIONS".equalsIgnoreCase(method1)){
                     return false;
              }


                 HandlerMethod handlerMethod = (HandlerMethod) handler;
         //获取方法
         Method method = handlerMethod.getMethod();
         //判断方法上是否加了自定义主角  如果有 继续后续的判断
         if(!method.isAnnotationPresent(Check.class)){
             return true;
         }

         String header = request.getHeader("x-auth");
        //头信息不存在
        if(StringUtils.isEmpty(header)){
            throw new GlobalException(ResponseEnum.MEMBER_HANDER_IS_NULL);
        }
        String[] split = header.split("\\.");
        //头信息丢失
        if(split.length!=2){
            throw new GlobalException(ResponseEnum.MEMBER_HANDER_IS_LOSE);
        }

        //用户的信息
        String base64=split[0];
        //会员的信息 加密钥 MD5加密   在编码
        String sign64=split[1];
        String sign = Md5Util.sign(base64, SystemConstant.SECRET);
        String encode = Base64.getEncoder().encodeToString(sign.getBytes());
        //头信息被篡改
        if(!sign64.equals(encode)){
            throw new GlobalException(ResponseEnum.MEMBER_HANDER_IS_ERROR);
        }

        String decode = new String(Base64.getDecoder().decode(base64));
        MemberVo memberVo = JSONObject.parseObject(decode, MemberVo.class);
        String memberName = memberVo.getMemberName();
        String uuid = memberVo.getUuid();
         Long id = memberVo.getId();
        boolean exists = RedisUtil.exists(KeyUtil.buildsign(id,memberName, uuid));
        if(!exists){
            throw new GlobalException(ResponseEnum.MEMBER_HANDER_IS_EXPIRE);
        }
        //续命
        RedisUtil.expire(KeyUtil.buildsign(id,memberName,uuid),SystemConstant.MUMBER_TIME);

      //存放用户名到request中
      request.setAttribute(SystemConstant.MEMBER,memberVo);
      return true;
    }
    
    
}
