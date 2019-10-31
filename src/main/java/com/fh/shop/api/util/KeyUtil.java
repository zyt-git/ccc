package com.fh.shop.api.util;

public class KeyUtil {

    public static String buildNote(String phone){
        return "note:"+phone;
    }

    public static String buildSMS(String phone){
        return "sms:"+phone;
    }


    public static String buildsign(Long id,String memberName,String uuid){
        return "sign:"+id+"."+memberName+"."+uuid;
    }


    public static String buildCartFiled(Long memberId){
        return "member:"+memberId;
    }

    public static String buildPayLogKey(Long memberId){
        return "payLog:"+memberId;
    }

}
