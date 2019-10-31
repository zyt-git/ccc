package com.fh.shop.api.orderby.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.orderby.biz.IOrderService;
import com.fh.shop.api.orderby.vo.OrderParm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Resource(name = "orderSerive")
    private IOrderService orderService;
    @Autowired
    HttpServletRequest request;

    @PostMapping
    @Check
    @Idempotent
    public ServiceResponse addOrder(OrderParm orderParm,MemberVo memberVo){
        Long memberId = memberVo.getId();
        return orderService.addOrder(memberId,orderParm);
    }


}
