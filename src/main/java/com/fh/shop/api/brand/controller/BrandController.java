package com.fh.shop.api.brand.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.common.ServiceResponse;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("brand")
public class BrandController {
    @Resource(name = "brandService")
    private IBrandService brandService;


    @RequestMapping("queryBrandJson")
    @Check
    public ServiceResponse queryAll(){
        ServiceResponse queryAll = brandService.queryAll();

        return queryAll;
    }

}
