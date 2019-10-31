package com.fh.shop.api.orderby.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
@Data
@TableName("t_order_detail")

public class OrderDetail {
    @TableId(type = IdType.INPUT)
    private String orderId;
    private Long memberId;
    private Long shopId;
    private String imgPath;
    private String shopName;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private Long count;


}
