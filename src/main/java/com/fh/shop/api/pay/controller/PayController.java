package com.fh.shop.api.pay.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.pay.biz.IPayService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.ws.rs.POST;

@RestController
@RequestMapping("pays")
public class PayController {
    @Resource(name = "payService")
    private IPayService payService;

    @PostMapping
    @Check
    public ServiceResponse createNative(MemberVo memberVo){
        Long memberId = memberVo.getId();
        return payService.createNative(memberId);
    }

    @PostMapping("updateStatus")
    @Check
    public ServiceResponse updatePayStatus(MemberVo memberVo){
        Long memberId = memberVo.getId();
        return payService.updatePayStatus(memberId);
    }

}
