package com.fh.shop.api.orderby.biz;

import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.orderby.vo.OrderParm;

public interface IOrderService {
    ServiceResponse addOrder(Long memberId,OrderParm orderParm);
}
