package com.fh.shop.api.area.controller;

import com.fh.shop.api.annotation.Check;
import com.fh.shop.api.area.biz.IAreaService;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.common.ServiceResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("areas")

public class AreaController {
    @Resource(name = "areaService")
    private IAreaService areaService;

    @GetMapping
    @Check
    public ServiceResponse queryAreapid( Long id){
        List<Area> areas = areaService.queryAreaPid(id);
        return ServiceResponse.success(areas);
    }



}
