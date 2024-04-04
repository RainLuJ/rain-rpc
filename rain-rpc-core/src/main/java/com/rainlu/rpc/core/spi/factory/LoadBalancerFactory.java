package com.rainlu.rpc.core.spi.factory;


import com.rainlu.rpc.core.loadbalancer.impl.RoundRobinLoadBalancer;
import com.rainlu.rpc.core.spi.SpiLoader;
import com.rainlu.rpc.core.spi.intf.LoadBalancer;

/**
 * 负载均衡器工厂（工厂模式，用于获取负载均衡器对象）
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }

}
