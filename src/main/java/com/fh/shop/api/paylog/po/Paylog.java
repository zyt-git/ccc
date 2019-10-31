package com.fh.shop.api.paylog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Paylog implements Serializable {
    @TableId(type = IdType.INPUT,value ="out_tradeId" )
    private String outTradeId;  //订单号  雪花算法
    private Long memberId;  //会员的id
    private String orderId; //订单的id
    private String transactionId; //交易流水号
    private Date createDate; //创建时间
    private Date payDate; //支付时间
    private BigDecimal payPrice; //支付金额
    private Integer payType; //支付平台
    private Integer payStatus; //支付状态  0 未支付 1 已支付  2 退款


}
