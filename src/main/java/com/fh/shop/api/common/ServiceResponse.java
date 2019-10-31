package com.fh.shop.api.common;

public class ServiceResponse {
    private Integer code;
    private String msg;
    private Object data;

    public static ServiceResponse success(){
        return new ServiceResponse(200,"ok",null);
    }
    public static ServiceResponse success(Object data){
        return new ServiceResponse(200,"ok",data);
    }

    public static ServiceResponse error(){
        return new ServiceResponse(-1,"error",null);
    }
    public static ServiceResponse error(Integer code,String msg){
        return new ServiceResponse(code,msg,null);
    }

    public static ServiceResponse error(ResponseEnum responseEnum){
        return new ServiceResponse(responseEnum.getCode(),responseEnum.getMsg(),null);
    }

    public static ServiceResponse LoginStatus(ResponseEnum responseEnum){
        return new ServiceResponse(responseEnum.getCode(),responseEnum.getMsg(),null);
    }

    private ServiceResponse(){}

    private ServiceResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
