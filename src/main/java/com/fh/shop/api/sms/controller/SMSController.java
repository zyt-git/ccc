package com.fh.shop.api.sms.controller;

import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.sms.biz.ISMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("sms")
public class SMSController {
    @Resource(name = "smsService")
    private ISMSService smsService;

    @GetMapping
    public ServiceResponse sendSmS(String phone){
        return smsService.sendMsm(phone);
    }

}
