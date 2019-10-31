package com.fh.shop.api.idempotent.controller;

import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.idempotent.biz.IdempotentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("idempotent")
public class IdempotentController {
    @Resource(name = "idempotentService")
    private IdempotentService idempotentService;

    @GetMapping
    public ServiceResponse createTocken(){
        return idempotentService.createToken();
    }

}
