package com.rainlu.rpc.core.config;

import com.rainlu.rpc.core.registry.constant.RegistryKeys;
import lombok.Data;

/**
 * RPC 框架注册中心配置
 * - 提供给用户的，让用户配置的用于连接注册中心的配置信息
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类别
     */
    private String registry = RegistryKeys.ETCD;

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;
}
