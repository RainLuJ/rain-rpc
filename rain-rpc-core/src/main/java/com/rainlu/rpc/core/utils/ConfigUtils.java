package com.rainlu.rpc.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * 配置工具类
 * - 工具类应当尽量通用，不要和某个业务作强绑定，要提高使用的灵活性。比如支持外层传入要读取的配置内容前缀、支持传入环境标识等。
 */
public class ConfigUtils {

    /**
     * 加载配置对象（默认加载无环境标识的配置文件）
     *
     * @param tClass
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix) {
        return loadConfig(tClass, prefix, "");
    }

    /**
     * 加载配置对象，支持区分环境
     *
     * @param tClass
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> tClass, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        // 在配置文件变更时实现自动加载
        props.autoLoad(true);
        return props.toBean(tClass, prefix);
    }
}
