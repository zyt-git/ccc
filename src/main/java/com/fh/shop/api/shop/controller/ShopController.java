package com.fh.shop.api.shop.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.shop.biz.IShopService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("shop")
public class ShopController {
    @Resource(name = "shopService")
    private IShopService shopService;

    @RequestMapping("queryShopJson")

    public ServiceResponse queryAll(){
        //获取数据
        ServiceResponse queryAll = shopService.queryAll();
        return queryAll;
    }
}
