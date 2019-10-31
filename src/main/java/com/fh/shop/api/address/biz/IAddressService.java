package com.fh.shop.api.address.biz;

import com.fh.shop.api.address.po.Address;
import com.fh.shop.api.common.ServiceResponse;

public interface IAddressService {
    ServiceResponse addAddress(Long memberId, Address address);

    ServiceResponse queryAddress(Long memberId);

    ServiceResponse delAddress(Long id);

    ServiceResponse updateAddress(Long memberId, Address address);

    ServiceResponse queryAddressById(Long id);
}
