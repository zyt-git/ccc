package com.fh.shop.api.orderby.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.orderby.mapper.IOrderDetailMapper;
import com.fh.shop.api.orderby.mapper.IOrderMapper;
import com.fh.shop.api.orderby.vo.OrderDetail;
import com.fh.shop.api.orderby.vo.OrderParm;
import com.fh.shop.api.orderby.vo.Orders;
import com.fh.shop.api.paylog.mapper.IPaylogMapper;
import com.fh.shop.api.paylog.po.Paylog;
import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.Shop;
import com.fh.shop.api.util.BigDecimailUtil;
import com.fh.shop.api.util.IdUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderSerive")
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderMapper orderMapper;
    @Autowired
    private IOrderDetailMapper orderDetailMapper;
    @Autowired
    private IShopMapper shopMapper;
    @Autowired
    private IPaylogMapper paylogMapper;

    @Override
    public ServiceResponse addOrder(Long memberId,OrderParm orderParm) {
        //从redis取出用户拥有的商品信息
        String hget = RedisUtil.hget(SystemConstant.CART_MAP, KeyUtil.buildCartFiled(memberId));
        if(StringUtils.isEmpty(hget)){
            return ServiceResponse.error(ResponseEnum.CART_IS_NULL);
        }
        //字符串转换成购物车对象
        CartVo cartVo = JSONObject.parseObject(hget, CartVo.class);
        //雪花算法 生成订单id

        String timeId = IdUtil.createId();
        //订单明细
        List<CartItemVo> list = cartVo.getList();
        List<CartItemVo> shortageList=new ArrayList<>();
        //CartItemVo cartItemVo=null;
        for (int i=list.size()-1;i>=0;i--) {
            CartItemVo cartItemVo = list.get(i);
            OrderDetail orderDetail=new OrderDetail();
            orderDetail.setCount(cartItemVo.getCount());
            orderDetail.setImgPath(cartItemVo.getImgPath());
            orderDetail.setMemberId(memberId);
            orderDetail.setOrderId(timeId);
            orderDetail.setPrice(new BigDecimal(cartItemVo.getPrice()));
            orderDetail.setShopId(cartItemVo.getShopId());
            orderDetail.setShopName(cartItemVo.getShopName());
            orderDetail.setTotalPrice(new BigDecimal(cartItemVo.getSubTotalCount()));

            Long count = cartItemVo.getCount();
            Long shopId = cartItemVo.getShopId();
            //根据shopId商品的信息
            Shop shop = shopMapper.selectById(shopId);
            //商品的库存
            Long stock = shop.getStock();
            if(stock>=count){
                //减少库存  返回值为1 可以购买  0  库存不足 不能购买
                Long aLong = shopMapper.updateShopStock(count, shopId);
                if(aLong==0){
                    shortageList.add(cartItemVo);
                    list.remove(cartItemVo);
                    //return ServiceResponse.error(ResponseEnum.SHOP_STOCK_IS_SHORT);
                }else{
                    orderDetailMapper.insert(orderDetail);
                }
            }else{
                shortageList.add(cartItemVo);
                list.remove(cartItemVo);
            }
        }
        //如果一条订单超卖
        if(list.size()==0){
            return ServiceResponse.error(ResponseEnum.SHOP_STOCK_IS_SHORT);
        }
        //更新数据
        updateCart(cartVo,memberId);


        //订单
        Orders orders=new Orders();
        orders.setId(timeId);
        orders.setCreateDate(new Date());
        orders.setMemberId(memberId);
        orders.setTotalPrice(new BigDecimal(cartVo.getTotalPrice()));
        orders.setStatus(SystemConstant.PAY_STATUS);
        orders.setPayType(orderParm.getBuyType());
        orders.setTotalCount(cartVo.getTotalCount());
        orders.setBill(orderParm.getBill());
        orderMapper.insert(orders);

        //支付的日志
        Paylog paylog=new Paylog();
        paylog.setCreateDate(new Date());
        paylog.setMemberId(memberId);
        paylog.setOrderId(timeId);
        paylog.setPayPrice(new BigDecimal(cartVo.getTotalPrice()));
        paylog.setPayType(orderParm.getBuyType());
        paylog.setOutTradeId(IdUtil.createId());
        paylog.setPayStatus(SystemConstant.PAY_STATUS);
        paylogMapper.insert(paylog);
        String paylogStr = JSONObject.toJSONString(paylog);
        RedisUtil.set(KeyUtil.buildPayLogKey(memberId),paylogStr);


        //  提交订单 删除购物车
        RedisUtil.hdel(SystemConstant.CART_MAP,KeyUtil.buildCartFiled(memberId));

        return ServiceResponse.success(shortageList);
    }

    private void updateCart(CartVo cartVo,Long memberId) {
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


}
