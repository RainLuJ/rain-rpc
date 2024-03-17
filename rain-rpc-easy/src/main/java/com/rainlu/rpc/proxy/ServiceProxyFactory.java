package com.rainlu.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * 代理对象创建工厂
 */
public class ServiceProxyFactory {

    /**
     * 获取为目标类创建的代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        /**
         * @param loader 使用反射获取 能加载到 目标对象 的类加载器。不论是什么类加载器，只要能加载到当前目标对象就可以了！！！！！！
         * 				简单的方案就是：创建目标类对象，通过这个对象，获取类加载器。
         * @param interfaces 使用反射获取目标对象实现的接口
         * @param h 开发者自己写的，在代理类中增强目标类的功能。就是InvocationHandler接口实现类。
         * @description 创建代理对象
         */
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }
}
