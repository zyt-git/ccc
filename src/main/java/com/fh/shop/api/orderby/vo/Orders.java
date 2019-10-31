package com.fh.shop.api.orderby.vo;





;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class Orders implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id;
    private Long memberId;
    private Date createDate; //创建日期
    private  Integer status;  //支付状态
    private  Integer payType;  //支付类型
    private BigDecimal totalPrice;
    private Date payDate;  //购买成功日期
    private String addressId;
    private Long totalCount;
    private Integer bill;  //发票




}
