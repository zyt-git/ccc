package com.fh.shop.api.brand.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.brand.vo.BrandVo;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.param.BrandSearch;
import com.fh.shop.api.shop.vo.ShopVo;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {
    @Autowired
    private IBrandMapper brandMapper;

    //查询热销
    @Override
    public ServiceResponse queryAll() {
        String brandListJson = RedisUtil.get("hotBrandList");
        if(StringUtils.isNotEmpty(brandListJson)){
            List<BrandVo> brandVoList = JSONObject.parseArray(brandListJson, BrandVo.class);
            return ServiceResponse.success(brandVoList);
        }
        QueryWrapper<Brand> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("hotCake",1);
        queryWrapper.orderByAsc("sort");
        List<Brand> brandList = brandMapper.selectList(queryWrapper);
        List<BrandVo> brandVoList = buildBrandVoList(brandList);
        //将java对象转换为json字符串
        String brandVoJson = JSONObject.toJSONString(brandVoList);
        RedisUtil.set("hotBrandList",brandVoJson);
        return ServiceResponse.success(brandVoList);
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insert(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        brandMapper.deleteById(id);
    }



    @Override
    public Brand queryBrandGetId(Long id) {
        return brandMapper.selectById(id);
    }

    @Override
    public ServiceResponse updateBrand(Brand brand) {
        brandMapper.updateById(brand);
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse deleteBathBrand(String ids) {
        if(StringUtils.isEmpty(ids)){
            return ServiceResponse.error(ResponseEnum.IDS_IS_NULL);
        }
        String[] idsArr = ids.split(",");
        List idsList=new ArrayList();
        for (String s : idsArr) {
            idsList.add(s);
        }
        brandMapper.deleteBatchIds(idsList);
        return ServiceResponse.success();
    }

    @Override
    public DataTableResult findPage(BrandSearch brandSearch) {
        Long brandCount = brandMapper.queryBrandCount(brandSearch);
        List<Brand> brandList = brandMapper.queryBrandSearch(brandSearch);
        List<BrandVo> brandVoList = buildBrandVoList(brandList);
        DataTableResult dataTableResult=new DataTableResult(brandSearch.getDraw(),brandCount,brandCount,brandVoList);
        return dataTableResult;
    }

    private List<BrandVo> buildBrandVoList(List<Brand> brandList) {
        List<BrandVo>  brandVoList=new ArrayList<>();
        for (Brand brand : brandList) {
            BrandVo brandVo=new BrandVo();
            brandVo.setBrandname(brand.getBrandname());
            brandVo.setHotCake(brand.getHotCake());
            brandVo.setSort(brand.getSort());
            brandVo.setImgPath(brand.getImgPath());
            brandVo.setId(brand.getId());
            brandVoList.add(brandVo);
        }
        return brandVoList;
    }
}
