package com.fh.shop.api.util;

import java.util.HashMap;
import java.util.Map;

public class SMSUtil {
    //发送验证码的请求路径URL
    private static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
            APP_KEY="ef0c520f4d4be30e3f4966c85768e292";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="ed5ccaf837a5";
    //随机数
    private static final String NONCE="123456";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="6";
    private static final String CURTIME = System.currentTimeMillis()+"";

    //短信模板ID
    private static final String TEMPLATEID="14798561";

    public static String sendMsg(String phone){
        //设置头信息
        Map<String, String> header=new HashMap<>();
        header.put("AppKey",APP_KEY);
        header.put("Nonce",NONCE);
        header.put("CurTime",CURTIME);
        header.put("CheckSum",CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,CURTIME));
        //设置参数
        Map<String, String> params=new HashMap<>();
        params.put("mobile",phone);
        params.put("codeLen",CODELEN);
        params.put("templateid", TEMPLATEID);
        String sms = HttpClientUtil.getHttpClient(SERVER_URL, header, params);
        return sms;
    }



}
