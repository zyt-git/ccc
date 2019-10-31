package com.fh.shop.api.brand.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.param.BrandSearch;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {

    List<Brand> queryBrandSearch(BrandSearch brandSearch);

    Long queryBrandCount(BrandSearch brandSearch);
}
