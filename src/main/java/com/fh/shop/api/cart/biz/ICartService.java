package com.fh.shop.api.cart.biz;


import com.fh.shop.api.common.ServiceResponse;

public interface ICartService {

    ServiceResponse add(Long shopId, Long count, Long memberId);

    ServiceResponse queryCart(Long memberId);

    ServiceResponse deleteCart(Long shopId, Long memberId);
}
