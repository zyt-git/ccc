package com.fh.shop.api.area.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.area.mapper.IAreaMapper;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {
    @Autowired
    private IAreaMapper areaMapper;

    @Override
    public List<Area> queryAreaPid(Long id) {
        String areaListStr = RedisUtil.get("areaList");
        if(StringUtils.isNotEmpty(areaListStr)){
            List<Area> areaList = JSONObject.parseArray(areaListStr, Area.class);
            List<Area> childList = findChildList(id, areaList);
            return childList;
        }
        List<Area> areaList = areaMapper.selectList(null);
        areaListStr = JSONObject.toJSONString(areaList);
        RedisUtil.set("areaList",areaListStr);
        return areaMapper.queryAreaPid(id);
    }

    public List<Area> findChildList(Long id,List<Area> areaList){
        List<Area> areaList1=new ArrayList<>();
        for (Area area : areaList) {
            if(area.getPid()==id){
                areaList1.add(area);
            }
        }
        return areaList1;
    }

}
