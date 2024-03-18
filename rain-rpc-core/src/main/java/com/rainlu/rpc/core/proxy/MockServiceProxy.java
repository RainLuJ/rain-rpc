package com.rainlu.rpc.core.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 为服务提供者中的服务提供服务Mock功能，以供测试
 * - 为什么需要Mock？
 * - 当服务提供者还未完善时，是无法对外提供服务调用的，此时使用Mock，可以保证消费者业务功能的正常运行，而不需要注释有关服务提供者的调用代码
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 根据方法的返回值类型，生成特定的默认值对象
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(methodReturnType);
    }

    /**
     * 生成指定类型的默认值对象
     *
     * @param type
     * @return
     */
    private Object getDefaultObject(Class<?> type) {
        // 基本类型及对应包装类的默认值
        if (type == boolean.class || type == Boolean.class) {
            return false;
        } else if (type == byte.class || type == Byte.class) {
            return (byte) 0;
        } else if (type == short.class || type == Short.class) {
            return (short) 0;
        } else if (type == int.class || type == Integer.class) {
            return 0;
        } else if (type == long.class || type == Long.class) {
            return 0L;
        } else if (type == float.class || type == Float.class) {
            return 0.0f;
        } else if (type == double.class || type == Double.class) {
            return 0.0d;
        } else if (type == char.class || type == Character.class) {
            return '\u0000';
        } else if (type.isArray()) { // 数组类型
            Class<?> componentType = type.getComponentType();
            return Array.newInstance(componentType, 0);
        }
        // 对于常见的对象类型（如String、Date等），提供默认空实例
        else if (type == String.class) {
            return "";
        } else if (type == java.util.Date.class) {
            return new Date(0); // 1970年1月1日零时
        } else if (type == java.sql.Date.class) {
            return new java.sql.Date(0);
        } else if (type == BigInteger.class) {
            return BigInteger.ZERO;
        } else if (type == BigDecimal.class) {
            return BigDecimal.ZERO;
        } else if (Enum.class.isAssignableFrom(type)) {
            // 获取并返回该枚举类的第一个枚举值
            Object[] enumConstants = type.getEnumConstants();
            if (enumConstants != null && enumConstants.length > 0) {
                return enumConstants[0];
            }
        }

        // 其他复杂对象类型或自定义类，通常情况下返回null
        return null;
    }
}
