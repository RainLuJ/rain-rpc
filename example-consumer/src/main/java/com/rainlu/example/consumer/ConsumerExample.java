package com.rainlu.example.consumer;

import com.rainlu.example.common.model.User;
import com.rainlu.example.common.service.UserService;
import com.rainlu.rpc.core.bootstrap.ConsumerBootstrap;
import com.rainlu.rpc.core.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */
public class ConsumerExample {

    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("yupi");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
