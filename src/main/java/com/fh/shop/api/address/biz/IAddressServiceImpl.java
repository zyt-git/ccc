package com.fh.shop.api.address.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.address.mapper.IAddressMapper;
import com.fh.shop.api.address.po.Address;
import com.fh.shop.api.address.vo.AddressVo;
import com.fh.shop.api.common.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("addressService")
public class IAddressServiceImpl implements IAddressService {
    @Autowired
    private IAddressMapper addressMapper;

    @Override
    public ServiceResponse addAddress(Long memberId, Address address) {
        address.setMemberId(memberId);
        addressMapper.insert(address);
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse queryAddress(Long memberId) {
        QueryWrapper<Address> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("member",memberId);
        List<Address> addressList = addressMapper.findAddress(memberId);
        List<AddressVo> addressVoList = buildAddressVoList(addressList);

        return ServiceResponse.success(addressVoList);
    }

    @Override
    public ServiceResponse delAddress(Long id) {
        addressMapper.deleteById(id);
        return ServiceResponse.success();
    }

    @Override
    public ServiceResponse updateAddress(Long memberId, Address address) {
        addressMapper.updateById(address);
        return  ServiceResponse.success();
    }

    @Override
    public ServiceResponse queryAddressById(Long id) {
        Address address = addressMapper.queryAddressById(id);
        return ServiceResponse.success(address);
    }

    private List<AddressVo> buildAddressVoList(List<Address> addressList) {
        List<AddressVo> addressVoList=new ArrayList<>();
        for (Address address : addressList) {
            AddressVo addressVo=new AddressVo();
            addressVo.setId(address.getId());
            addressVo.setAddressName(address.getAddressName());
            addressVo.setAllAddressInfo(address.getAllAddressInfo());
            addressVo.setAllAreas(address.getAllAreas());
            addressVoList.add(addressVo);
        }
        return addressVoList;
    }


}
