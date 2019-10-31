package com.fh.shop.api.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.shop.po.Shop;
import org.apache.ibatis.annotations.Param;

public interface IShopMapper extends BaseMapper<Shop> {
    Long updateShopStock(@Param("count") Long count,@Param("shopId") Long shopId);
}
