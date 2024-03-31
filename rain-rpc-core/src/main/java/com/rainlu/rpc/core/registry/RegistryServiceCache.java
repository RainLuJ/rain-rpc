package com.rainlu.rpc.core.registry;


import com.rainlu.rpc.core.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心服务本地缓存
 *   - 正常情况下，服务节点信息列表的更新频率是不高的，所以可以在服务消费者从注册中心获取到服务节点信息列表后，将信息缓存在本地
 */
public class RegistryServiceCache {

    /**
     * 服务缓存
     */
    public List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     *
     * @param newServiceCache
     * @return
     */
    public void writeCache(List<ServiceMetaInfo> newServiceCache) {
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     *
     * @return
     */
    public List<ServiceMetaInfo> readCache() {
        return this.serviceCache;
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        this.serviceCache = null;
    }
}
