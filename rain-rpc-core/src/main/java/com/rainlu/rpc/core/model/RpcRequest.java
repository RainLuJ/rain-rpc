package com.rainlu.rpc.core.model;

import com.rainlu.rpc.core.constant.RpcConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RPC 请求参数封装
 * - 封装接口调用时所需的参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequest implements Serializable {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 服务版本
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 参数类型列表（便于直接通过反射调用指定参数类型列表的方法-因为有存在重载方法的可能）
     *
     * private 参数类型 参数值
     */
    private Class<?>[] parameterTypes;

    /**
     * 参数列表
     *
     * private 参数类型 参数值
     */
    private Object[] args;

}
