package com.rainlu.rpc.springboot.starter.bootstrap;

import com.rainlu.rpc.core.proxy.ServiceProxyFactory;
import com.rainlu.rpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * Rpc 服务消费者启动
 * 1. 找到带有@RpcReference注解的属性
 * 2. 获取该属性的代理对象
 * 3. 使用反射将代理对象赋值给属性
 */
@Slf4j
public class RpcConsumerBootstrap implements BeanPostProcessor {

    /**
     * 在Bean初始化之后进行处理的回调方法。
     * 该方法会遍历指定Bean的所有属性，寻找被@RpcReference注解标记的属性，
     * 并为这些属性生成代理对象，然后注入到Bean中。
     *
     * @param bean 刚刚被初始化的Bean实例。
     * @param beanName Bean的名称。
     * @return 处理后的Bean实例。
     * @throws BeansException 如果处理过程中发生错误。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        // 遍历对象的所有属性
        Field[] declaredFields = beanClass.getDeclaredFields();
        for (Field field : declaredFields) {
            RpcReference rpcReference = field.getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                // 为被@RpcReference注解标记的属性生成代理对象
                Class<?> interfaceClass = rpcReference.interfaceClass();
                if (interfaceClass == void.class) {
                    interfaceClass = field.getType();
                }
                field.setAccessible(true);
                Object proxyObject = ServiceProxyFactory.getProxy(interfaceClass);
                try {
                    field.set(bean, proxyObject);
                    field.setAccessible(false); // 确保字段的访问性恢复原状
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("为字段注入代理对象失败", e);
                }
            }
        }
        // 假设接口中存在default的默认方法实现，则实现类可以通过“接口名.super.默认方法”的形式调用其实现的接口中的默认方法
        // 在这里再次调用BeanPostProcessor中原默认方法的作用是确保Bean的属性注入正确（仍然使用的原来的bean）
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
