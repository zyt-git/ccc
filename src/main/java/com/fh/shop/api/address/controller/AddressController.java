package com.fh.shop.api.address.controller;

import com.fh.shop.api.address.biz.IAddressService;
import com.fh.shop.api.address.po.Address;
import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.member.vo.MemberVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("address")
public class AddressController {
    @Resource(name = "addressService")
    private IAddressService addressService;

    @PostMapping
    @Check
    public ServiceResponse addAddress(Address address, MemberVo memberVo){
        Long memberId = memberVo.getId();
        return addressService.addAddress(memberId,address);
    }

    @GetMapping
    @Check
    public ServiceResponse queryAddress( MemberVo memberVo){
        Long memberId = memberVo.getId();
        return addressService.queryAddress(memberId);
    }

    @DeleteMapping
    public ServiceResponse delAddress(Long id){
        return addressService.delAddress(id);
    }

    @GetMapping("queryAddressId")
    @Check
    public ServiceResponse queryAddressById(Long id){
        return addressService.queryAddressById(id);
    }

    @PutMapping
    @Check
    public ServiceResponse updateAddress( MemberVo memberVo,@RequestBody Address address){
        Long memberId = memberVo.getId();
        return addressService.updateAddress(memberId,address);
    }

}
