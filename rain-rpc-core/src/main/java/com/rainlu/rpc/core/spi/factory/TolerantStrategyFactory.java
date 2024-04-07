package com.rainlu.rpc.core.spi.factory;

import com.rainlu.rpc.core.fault.tolerant.impl.FailFastTolerantStrategy;
import com.rainlu.rpc.core.spi.SpiLoader;
import com.rainlu.rpc.core.spi.intf.TolerantStrategy;

/**
 * 容错策略工厂（工厂模式，用于获取容错策略对象）
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /**
     * 默认容错策略
     */
    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }

}
