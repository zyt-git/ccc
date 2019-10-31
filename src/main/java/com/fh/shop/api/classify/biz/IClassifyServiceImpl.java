package com.fh.shop.api.classify.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.classify.mapper.IClassifyMapper;
import com.fh.shop.api.classify.po.Classify;
import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classifyService")
public class IClassifyServiceImpl implements IClassifyService {
    @Autowired
    private IClassifyMapper classifyMapper;

    @Override
    public ServiceResponse queryAll() {
        String classifyListStr = RedisUtil.get("classifyList");
        if(StringUtils.isNotEmpty(classifyListStr)){
            List<Classify> classifyList = JSONObject.parseArray(classifyListStr, Classify.class);
            return ServiceResponse.success(classifyList);
        }
        List<Classify> classifies = classifyMapper.selectList(null);
        classifyListStr = JSONObject.toJSONString(classifies);
        RedisUtil.set("classifyList",classifyListStr);
        return ServiceResponse.success(classifies);
    }
}
