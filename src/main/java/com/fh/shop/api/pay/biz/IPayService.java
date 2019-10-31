package com.fh.shop.api.pay.biz;

import com.fh.shop.api.common.ServiceResponse;

public interface IPayService {
    ServiceResponse createNative(Long memberId);

    ServiceResponse updatePayStatus(Long memberId);
}
