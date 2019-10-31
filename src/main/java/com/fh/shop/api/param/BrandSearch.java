package com.fh.shop.api.param;

import com.fh.shop.api.common.Page;

import java.io.Serializable;

public class BrandSearch extends Page implements Serializable {
    private String brandname;

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }
}
