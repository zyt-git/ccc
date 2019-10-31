package com.fh.shop.api.idempotent.biz;

import com.fh.shop.api.common.ServiceResponse;

public interface IdempotentService {
    ServiceResponse createToken();
}
