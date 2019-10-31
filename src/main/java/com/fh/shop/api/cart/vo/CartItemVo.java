package com.fh.shop.api.cart.vo;



public class CartItemVo {
    private Long shopId;
    private String shopName;
    private String price;
    private Long count;
    private String imgPath;
    private String subTotalCount;


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getSubTotalCount() {
        return subTotalCount;
    }

    public void setSubTotalCount(String subTotalCount) {
        this.subTotalCount = subTotalCount;
    }
}
