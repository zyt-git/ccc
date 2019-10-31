package com.fh.shop.api.brand.vo;

import java.io.Serializable;

public class BrandVo implements Serializable {
    private Long id;
    private String brandname;
    private Integer hotCake;
    private Integer sort;
    private String imgPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getHotCake() {
        return hotCake;
    }

    public void setHotCake(Integer hotCake) {
        this.hotCake = hotCake;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }
}
