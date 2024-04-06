package com.rainlu.rpc.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务注册信息类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterInfo<T> {

    /**
     * 服务名称（接口的全限定名称）
     */
    private String serviceName;

    /**
     * 实现类
     */
    private Class<? extends T> implClass;
}
