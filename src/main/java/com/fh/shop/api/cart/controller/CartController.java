package com.fh.shop.api.cart.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("carts")
public class CartController {
    @Resource(name = "cartService")
    private ICartService cartService;
    @Autowired
    private HttpServletRequest request;

   @PostMapping
   @Check
    public ServiceResponse addCart(Long shopId,Long count,MemberVo memberVo){
       Long memberId = memberVo.getId();
       return cartService.add( shopId, count, memberId);
   }


   @GetMapping
   @Check
    public ServiceResponse queryCart(MemberVo memberVo){
       Long memberId = memberVo.getId();
       return cartService.queryCart(memberId);
   }

    @DeleteMapping
    @Check
    public ServiceResponse deleteCart( Long shopId,MemberVo memberVo){
       Long memberId = memberVo.getId();
       return cartService.deleteCart(shopId,memberId);
   }

}
