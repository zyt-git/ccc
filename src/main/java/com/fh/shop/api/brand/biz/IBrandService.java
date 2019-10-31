package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.param.BrandSearch;

public interface IBrandService {
    ServiceResponse queryAll();

    void addBrand(Brand brand);

    void deleteBrand(Long id);



    Brand queryBrandGetId(Long id);

    ServiceResponse updateBrand(Brand brand);

    ServiceResponse deleteBathBrand(String ids);

    DataTableResult findPage(BrandSearch brandSearch);
}
