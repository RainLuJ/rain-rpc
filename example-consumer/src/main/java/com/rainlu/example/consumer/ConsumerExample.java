package com.rainlu.example.consumer;

import com.rainlu.example.common.service.UserService;
import com.rainlu.rpc.RpcApplication;
import com.rainlu.rpc.core.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {

        System.out.println(RpcApplication.getRpcConfig());

        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        System.out.println(userService.getAge());

    }
}
