//package com.rainlu.example.consumer;
//
//import com.rainlu.example.common.model.User;
//import com.rainlu.example.common.service.UserService;
//import com.rainlu.rpc.proxy.ServiceProxyFactory;
//
///**
// * 简易服务消费者示例
// */
//public class EasyConsumerExample {
//
//    public static void main(String[] args) {
//
//        // 通过RPC框架，得到一个支持远程调用服务提供者的代理对象
//        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
//        User user = new User();
//        user.setName("rainlu");
//        // 调用
//        User newUser = userService.getUser(user);
//        if (newUser != null) {
//            System.out.println(newUser.getName());
//        } else {
//            System.out.println("user == null");
//        }
//    }
//}
