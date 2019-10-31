package com.fh.shop.api.idempotent.biz;

import com.fh.shop.api.common.ServiceResponse;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("idempotentService")
public class IdempotentServiceImpl implements IdempotentService {
    @Override
    public ServiceResponse createToken() {
        String uuid = UUID.randomUUID().toString();
        RedisUtil.set(uuid,uuid);
        return ServiceResponse.success(uuid);
    }
}
