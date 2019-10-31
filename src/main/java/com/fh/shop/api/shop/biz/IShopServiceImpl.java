package com.fh.shop.api.shop.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.Shop;
import com.fh.shop.api.shop.vo.ShopVo;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("shopService")
public class IShopServiceImpl implements IShopService {
    @Autowired
    private IShopMapper shopMapper;

    @Override
    public ServiceResponse queryAll() {
        String ShowShopListJson = RedisUtil.get("showShopList");
        if(StringUtils.isNotEmpty(ShowShopListJson)){
            List<ShopVo> shopVoList = JSONObject.parseArray(ShowShopListJson, ShopVo.class);
            return ServiceResponse.success(shopVoList);
        }
        QueryWrapper<Shop> queryWrapper=new QueryWrapper<>();
         queryWrapper.orderByDesc("id");
         queryWrapper.eq("isJia",1);
        List<Shop> shopList = shopMapper.selectList(queryWrapper);
        List<ShopVo> shopVoList = buildShopVoList(shopList);
        //将java对象转换为json字符串
        String shopVoJson = JSONObject.toJSONString(shopVoList);
        RedisUtil.setex("showShopList",shopVoJson,30);
        return ServiceResponse.success(shopVoList);
    }

    private List<ShopVo> buildShopVoList(List<Shop> shopList) {
        List<ShopVo>  shopVoList=new ArrayList<>();
        for (Shop shop : shopList) {
            ShopVo shopVo = buildShopVo(shop);
            shopVoList.add(shopVo);
        }
        return shopVoList;
    }

    private ShopVo buildShopVo(Shop shop) {
        ShopVo shopVo=new ShopVo();
        shopVo.setId(shop.getId());
        shopVo.setShopname(shop.getShopname());
        shopVo.setPrice(shop.getPrice().toString());
        Date productdate = shop.getProductdate();
        String s = DateUtil.date2str(productdate, DateUtil.Y_M_D);
        shopVo.setProductdate(s);
        shopVo.setStock(shop.getStock());
        shopVo.setIsHot(shop.getIsHot());
        shopVo.setIsJia(shop.getIsJia());
        shopVo.setBrandId(shop.getBrandId());
        shopVo.setImgPath(shop.getImgPath());
        return shopVo;
    }
}
