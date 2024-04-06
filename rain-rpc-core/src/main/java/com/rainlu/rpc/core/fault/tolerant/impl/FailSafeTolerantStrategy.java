package com.rainlu.rpc.core.fault.tolerant.impl;

import com.rainlu.rpc.core.model.RpcResponse;
import com.rainlu.rpc.core.spi.intf.TolerantStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 容错策略 - 静默处理异常
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // 只记录个日志，代表服务调用出现了异常
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
