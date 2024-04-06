package com.rainlu.rpc.core.fault.tolerant.impl;

import com.rainlu.rpc.core.model.RpcResponse;
import com.rainlu.rpc.core.spi.intf.TolerantStrategy;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 */
public class FailFastTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // 立刻抛出异常
        throw new RuntimeException("服务报错", e);
    }
}
