package com.fh.shop.api.exception;


import com.fh.shop.api.common.ServiceResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ServiceResponse getGlobal(GlobalException g){
        return ServiceResponse.error(g.getResponseEnum());
    }
}
