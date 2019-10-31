package com.fh.shop.api.area.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.area.po.Area;

import java.util.List;

public interface IAreaMapper extends BaseMapper<Area> {
    List<Area> queryAreaPid(Long id);
}
