package com.fh.shop.api.sms.biz;

import com.fh.shop.api.common.ServiceResponse;

public interface ISMSService {
    ServiceResponse sendMsm(String phone);
}
