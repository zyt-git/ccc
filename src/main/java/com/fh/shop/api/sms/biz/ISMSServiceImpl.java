package com.fh.shop.api.sms.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.sms.Result;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SMSUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("smsService")
public class ISMSServiceImpl implements ISMSService {
    @Override
    public ServiceResponse sendMsm(String phone) {
        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
        Matcher matcher = p.matcher(phone);
        boolean b = matcher.find();
        if(!b){
            return  ServiceResponse.error(ResponseEnum.MEMBER_PHONE_STYLE_ERROE);
        }
        if(StringUtils.isEmpty(phone)){
            return ServiceResponse.error(ResponseEnum.REGISTER_NOTE_IS_NULL);
        }

        String msg = SMSUtil.sendMsg(phone);
        Result result = JSONObject.parseObject(msg, Result.class);
        int code = result.getCode();
        if(code!=200){
            return ServiceResponse.error(ResponseEnum.REGISTER_NOTE_IS_ERROR);
        }
        String obj = result.getObj();
        RedisUtil.setex(KeyUtil.buildSMS(phone),obj,SystemConstant.SMS_EXPRIE);

        return ServiceResponse.success();
    }
}
