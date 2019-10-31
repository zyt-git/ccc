package com.fh.shop.api.pay.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.orderby.mapper.IOrderMapper;
import com.fh.shop.api.orderby.vo.Orders;
import com.fh.shop.api.paylog.mapper.IPaylogMapper;
import com.fh.shop.api.paylog.po.Paylog;
import com.fh.shop.api.util.*;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class IPayServiceImpl implements IPayService {
    @Autowired
    private IPaylogMapper paylogMapper;
    @Autowired
    private IOrderMapper orderMapper;

    @Override
    public ServiceResponse createNative(Long memberId) {
        //获取支付日志
        String paylogStr = RedisUtil.get(KeyUtil.buildPayLogKey(memberId));
        //非空判断
        if(StringUtils.isEmpty(paylogStr)){
            return ServiceResponse.error(ResponseEnum.PAYLOG_IS_NULL);
        }
        //转换成支付日志对象
        Paylog paylog = JSONObject.parseObject(paylogStr, Paylog.class);
        //获取订单编号
        String outTradeId = paylog.getOutTradeId();
        //获取到支付金额
        BigDecimal payPrice = paylog.getPayPrice();
        String priceStr = payPrice.toString();
        int price = BigDecimailUtil.mul(paylog.getPayPrice().toString(), "100").intValue();
        MyConfig config = new MyConfig();
        try {
            //微信二维码默认过期时间是2小时
            //自己设置二维码过期时间是两分钟
            Date date = DateUtils.addMinutes(new Date(), 2);
            String dateStr = DateUtil.date2str(date, DateUtil.YYYYMMDDHHMMSS);
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", outTradeId);
            data.put("time_expire", dateStr);
            data.put("body", "充值金额");
            data.put("fee_type", "CNY");
            data.put("total_fee", price+"");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            //下单
            String returnCode = resp.get("return_code");
            String returnMsg = resp.get("return_msg");
            if(!returnCode.equalsIgnoreCase("SUCCESS")){
                System.out.println(returnMsg);
                return ServiceResponse.error(7777,"微信支付错误"+returnMsg);
            }
            String resultCode = resp.get("result_code");
            String errCodeDes = resp.get("err_code_des");
            if(!resultCode.equalsIgnoreCase("SUCCESS")){
                System.out.println(errCodeDes);
                return ServiceResponse.error(7777,"微信支付错误"+returnMsg);
            }
            String codeUrl = resp.get("code_url");

            Map map=new HashMap();
            map.put("code_url",codeUrl);
            map.put("outTradeId",outTradeId);
            map.put("payPrice",priceStr);

            return ServiceResponse.success(map
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResponse.error();
        }

    }

    @Override
    public ServiceResponse updatePayStatus(Long memberId) {
        //获取支付日志
        String paylogStr = RedisUtil.get(KeyUtil.buildPayLogKey(memberId));
        //非空判断
        if(StringUtils.isEmpty(paylogStr)){
            return ServiceResponse.error(ResponseEnum.PAYLOG_IS_NULL);
        }
        //转换成支付日志对象
        Paylog paylog = JSONObject.parseObject(paylogStr, Paylog.class);
        //获取订单编号
        String outTradeId = paylog.getOutTradeId();
        String orderId = paylog.getOrderId();



        MyConfig config = new MyConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", outTradeId);
            int count=0;
            while(true){
                Map<String, String> resp = wxpay.orderQuery(data);
                System.out.println(resp);
                count++;
                String returnCode = resp.get("return_code");
                String returnMsg = resp.get("return_msg");
                if(!returnCode.equalsIgnoreCase("SUCCESS")){
                    System.out.println(returnMsg);
                    return ServiceResponse.error(7777,"微信支付错误"+returnMsg);
                }
                String resultCode = resp.get("result_code");
                String errCodeDes = resp.get("err_code_des");
                if(!resultCode.equalsIgnoreCase("SUCCESS")){
                    System.out.println(errCodeDes);
                    return ServiceResponse.error(7777,"微信支付错误"+returnMsg);
                }

                String tradeState = resp.get("trade_state");
                if(tradeState.equalsIgnoreCase("SUCCESS")){
                    System.out.println("----支付成功---");
                    //修改支付日志的状态
                    Paylog paylog1=new Paylog();
                    paylog1.setOutTradeId(outTradeId);
                    paylog1.setPayDate(new Date());
                    paylog1.setPayStatus(SystemConstant.PAY_STATUS_SUCCESS);
                    String transactionId = resp.get("transaction_id");
                    paylog1.setTransactionId(transactionId);
                    paylogMapper.updateById(paylog1);
                    //修改订单的状态
                    Orders orders=new Orders();
                    orders.setId(orderId);
                    orders.setPayDate(new Date());
                    orders.setStatus(SystemConstant.PAY_STATUS_SUCCESS);
                    orderMapper.updateById(orders);
                    //清除redispaylog缓存
                    RedisUtil.del(KeyUtil.buildPayLogKey(memberId));
                    return ServiceResponse.success();
                }
                if(count>50){
                    return ServiceResponse.error(ResponseEnum.PAY_SHOP_STSTUS_IX_EXPIRE);
                }
                new Thread().sleep(3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResponse.error();
        }

    }
}
