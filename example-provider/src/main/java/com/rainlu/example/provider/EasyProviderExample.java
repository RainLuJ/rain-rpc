package com.rainlu.example.provider;

import com.rainlu.example.common.service.UserService;
import com.rainlu.rpc.core.registry.local.LocalRegistry;
import com.rainlu.rpc.core.server.HttpServer;
import com.rainlu.rpc.core.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 向本地注册器注册服务
        System.out.println("注册服务：" + UserService.class.getName());
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
