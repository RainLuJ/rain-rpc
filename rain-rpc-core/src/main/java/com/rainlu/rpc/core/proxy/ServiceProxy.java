package com.rainlu.rpc.core.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.rainlu.rpc.RpcApplication;
import com.rainlu.rpc.core.model.RpcRequest;
import com.rainlu.rpc.core.model.RpcResponse;
import com.rainlu.rpc.core.serializer.Serializer;
import com.rainlu.rpc.core.serializer.SerializerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description 为服务提供者中的服务提供代理功能（JDK 动态代理）
 * @author Jun Lu
 * @date 2024-03-17 19:48:53
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * @param proxy  JDK创建的代理对象，由JDK提供。一般不会使用
     * @param method 当前所代理的目标类中的方法，由JDK提供
     * @param args   传递给目标类中方法的参数，由JDK提供
     *
     * 使用方法：
     *   1、创建类实现接口InvocationHandler
     *   2、重写invoke()方法，把原来静态代理中的代理类要实现的功能所需的参数写在“()”中
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器（使用SPI机制获取序列化器实现类）
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        // 构造请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 发送请求
            // todo 注意，这里地址被硬编码了（需要使用注册中心和服务发现机制解决）
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
