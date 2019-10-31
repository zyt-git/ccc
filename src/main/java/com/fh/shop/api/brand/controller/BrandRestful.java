package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.param.BrandSearch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("brands")
public class BrandRestful {
    @Resource(name = "brandService")
    private IBrandService brandService;

    @RequestMapping(method = RequestMethod.GET)

    public ServiceResponse queryPage(BrandSearch brandSearch){
        DataTableResult page = brandService.findPage(brandSearch);
        return ServiceResponse.success(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ServiceResponse addBrand(Brand brand){
        brandService.addBrand(brand);
        return ServiceResponse.success();
    }




    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ServiceResponse deleteBrand(@PathVariable("id") Long BrandId){
        brandService.deleteBrand(BrandId);
        return ServiceResponse.success();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ServiceResponse queryBrandGetId(@PathVariable Long id){
        Brand brand = brandService.queryBrandGetId(id);
        return ServiceResponse.success(brand);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ServiceResponse updateBrand(@RequestBody Brand brand){
        return brandService.updateBrand(brand);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ServiceResponse deleteBathBrand( String ids){
        return brandService.deleteBathBrand(ids);
    }


}
