package com.fh.shop.api.cart.vo;


import com.fh.shop.api.shop.po.Shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartVo implements Serializable {
    private Long totalCount;
    private String totalPrice;
    private List<CartItemVo> list=new ArrayList<>();


    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemVo> getList() {
        return list;
    }

    public void setList(List<CartItemVo> list) {
        this.list = list;
    }
}
