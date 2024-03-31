package com.rainlu.rpc.core.spi.intf;

import com.rainlu.rpc.core.config.RegistryConfig;
import com.rainlu.rpc.core.model.ServiceMetaInfo;

import java.util.List;

/**
 * 一个成熟的RPC框架可能会支持多个注册中心。
 * 为遵循可扩展设计，先写一个注册中心接口，后续可以实现多种不同的注册中心，并且和序列化器一样，可以使用 SPI机制动态加载。
 * 主要是提供了初始化、注册服务、注销服务、服务发现(获取已注册的服务节点列表)、服务销毁等方法。
 */
public interface Registry {

    /**
     * 初始化
     *  - 读取用户填写的注册中心配置
     *  - 初始化注册中心java连接客户端对象
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     *  - 创建key(需要设置过期时间)-value(服务注册信息`ServiceMetaInfo`的JSON串格式)
     *  - 存入到注册中心
     *
     * @param serviceMetaInfo
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     *  - 从注册中心中删除key-value
     *
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现
     *  - 将服务名称作为prefix，从注册中心中获取某服务的所有节点(消费端调用)
     *
     * @param serviceKey 服务键名
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 心跳检测（服务端）
     */
    void heartBeat();

    /**
     * 监听（消费端）
     *
     * @param serviceNodeKey
     */
    void watch(String serviceNodeKey);

    /**
     * 服务销毁
     *  - 在项目被关闭时，释放资源
     *  - 配合com.rainlu.rpc.RpcApplication#init()方法完成【主动下线】
     *      - 使用`Shutdown Hook`在JVM即将关闭之前执行一些清理工作或其他必要的操作
     */
    void destroy();
}
