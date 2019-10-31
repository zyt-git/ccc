package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.Shop;
import com.fh.shop.api.util.BigDecimailUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {
    @Autowired
    private IShopMapper shopMapper;

    @Override
    public ServiceResponse add(Long shopId, Long count, Long memberId) {
        //判断商品是否存在
        QueryWrapper<Shop> shopQueryWrapper=new QueryWrapper<>();
        shopQueryWrapper.eq("id",shopId);
        Shop shop = shopMapper.selectOne(shopQueryWrapper);
        if(shop==null){
            return ServiceResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //商品是否上架
        Integer isJia = shop.getIsJia();
        if(isJia==SystemConstant.SHOP_STATUS){
            return ServiceResponse.error(ResponseEnum.SHOP_IS_STATUS);
        }
        //是否有购物车
        String hget = RedisUtil.hget(SystemConstant.CART_MAP, KeyUtil.buildCartFiled(memberId));
        if(StringUtils.isEmpty(hget)){
            //没有就创建购物车
            CartVo cartVo=new CartVo();
            CartItemVo cartItemVo = buildCartItemCartVo(count, shop);
            //将商品信息添加到购物车
            cartVo.getList().add(cartItemVo);
            buildCart(cartVo,memberId);
            return ServiceResponse.success();
        }
        //有购物车 就判断车里是否有这条商品
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);


        List<CartItemVo> list = cartVo.getList();
        //没有 就创建一条商品
        CartItemVo cartItemVo = getCartItemVo(list, shopId);
        if(cartItemVo==null){
            CartItemVo cartItemVo1 = buildCartItemCartVo(count, shop);
            cartVo.getList().add(cartItemVo1);
            buildCart(cartVo,memberId);
            return ServiceResponse.success();
        }




        Long count1 = cartItemVo.getCount();

        if(count1+count==0){
            delCartItemVo(list,shopId);
        }else{
            cartItemVo.setCount(count1+count);

            String  subTotalCount = cartItemVo.getSubTotalCount();
            String mul = BigDecimailUtil.mul(cartItemVo.getPrice(), count.toString()).toString();
            cartItemVo.setSubTotalCount(BigDecimailUtil.add(mul,subTotalCount).toString());
        }

        buildCart(cartVo,memberId);
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse queryCart(Long memberId) {
        String hget = RedisUtil.hget(SystemConstant.CART_MAP, KeyUtil.buildCartFiled(memberId));
        if(StringUtils.isEmpty(hget)){
            return ServiceResponse.error(ResponseEnum.CART_IS_NULL);
        }
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);
        return ServiceResponse.success(cartVo);
    }

    @Override
    public ServiceResponse deleteCart(Long shopId, Long memberId) {
        String hget = RedisUtil.hget(SystemConstant.CART_MAP, KeyUtil.buildCartFiled(memberId));
        //判断有没有购物车
        if(StringUtils.isEmpty(hget)){
            return ServiceResponse.error(ResponseEnum.CART_IS_NULL);
        }
        //判断商品是否存在
        QueryWrapper<Shop> shopQueryWrapper=new QueryWrapper<>();
        shopQueryWrapper.eq("id",shopId);
        Shop shop = shopMapper.selectOne(shopQueryWrapper);
        if(shop==null){
            return ServiceResponse.error(ResponseEnum.SHOP_IS_NULL);
        }

        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);

        //购物车是否有该商品
        boolean cartItem = deleteCartFromCartItem(shopId, cartVo);
        if(!cartItem){
            return ServiceResponse.error(ResponseEnum.CART_CARTITEM_IS_NULL);
        }
        //更新购物车
        buildCart(cartVo,memberId);
        return ServiceResponse.success();
    }

    private boolean deleteCartFromCartItem(Long shopId, CartVo cartVo) {
        boolean result=false;
        List<CartItemVo> list = cartVo.getList();
            for (CartItemVo cartItemVo : list) {
                if(cartItemVo.getShopId()==shopId){
                    list.remove(cartItemVo);
                    result=true;
                    break;
                }
        }

        return result;
    }

    private void buildCart(CartVo cartVo,Long memberId) {
        List<CartItemVo> list = cartVo.getList();
        if(list.size()==0){
            RedisUtil.hdel(SystemConstant.CART_MAP,KeyUtil.buildCartFiled(memberId));
        }else{
            BigDecimal bigDecimal = new BigDecimal(0);
            Long totalCount = 0l;
            for (CartItemVo itemVo : list) {
                bigDecimal = BigDecimailUtil.add(bigDecimal.toString(),itemVo.getSubTotalCount());
                totalCount +=itemVo.getCount();
            }
            cartVo.setTotalPrice(bigDecimal.toString());
            cartVo.setTotalCount(totalCount);
            String s = JSONObject.toJSONString(cartVo);
            RedisUtil.hset(SystemConstant.CART_MAP,s,KeyUtil.buildCartFiled(memberId));
        }

    }

    private CartItemVo buildCartItemCartVo(Long count, Shop shop) {
        CartItemVo cartItemVo=new CartItemVo();
        cartItemVo.setCount(count);

        cartItemVo.setImgPath(shop.getImgPath());
        String shopPrice = shop.getPrice().toString();
        cartItemVo.setPrice(shopPrice);
        String mul = BigDecimailUtil.mul(shopPrice, count.toString()).toString();
        cartItemVo.setShopName(shop.getShopname());
        cartItemVo.setShopId(shop.getId());
        cartItemVo.setSubTotalCount(mul);
        return cartItemVo;
    }

    private static CartItemVo getCartItemVo(List<CartItemVo> list,Long shopId){
        for (CartItemVo cartItemVo : list) {
            if(cartItemVo.getShopId()==shopId){
                return cartItemVo;
            }
        }
        return null;
    }

    private static void delCartItemVo(List<CartItemVo> list,Long shopId){
        for (CartItemVo cartItemVo : list) {
            if(cartItemVo.getShopId()==shopId){
                list.remove(cartItemVo);
                break;
            }
        }
    }

}
