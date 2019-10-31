package com.fh.shop.api.shop.po;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Shop implements Serializable {
    private Long id;
    private String shopname;
    private BigDecimal price;
    private String imgPath;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date productdate;

    private Long stock;
    private Integer isHot;
    private Integer isJia;
    /*@TableField(exist = false) //代表字段在数据库里不存在*/
    private Long brandId;


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsJia() {
        return isJia;
    }

    public void setIsJia(Integer isJia) {
        this.isJia = isJia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Date getProductdate() {
        return productdate;
    }

    public void setProductdate(Date productdate) {
        this.productdate = productdate;
    }


    public Shop(Long id, String shopname, BigDecimal price, String imgPath, Date productdate, Long stock, Integer isHot, Integer isJia, Long brandId) {
        this.id = id;
        this.shopname = shopname;
        this.price = price;
        this.imgPath = imgPath;
        this.productdate = productdate;
        this.stock = stock;
        this.isHot = isHot;
        this.isJia = isJia;
        this.brandId = brandId;
    }

    public Shop(){}
}
