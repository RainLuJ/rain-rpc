package com.rainlu.rpc.core.protocol;

import lombok.Getter;

/**
 * 协议消息的类型枚举
 */
@Getter
public enum ProtocolMessageTypeEnum {

    // 请求
    REQUEST(0),
    // 响应
    RESPONSE(1),
    // 心跳
    HEART_BEAT(2),
    // 其它
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    /**
     * 根据 key 获取枚举
     *
     * @param key
     * @return
     */
    public static ProtocolMessageTypeEnum getEnumByKey(int key) {
        for (ProtocolMessageTypeEnum anEnum : ProtocolMessageTypeEnum.values()) {
            if (anEnum.key == key) {
                return anEnum;
            }
        }
        return null;
    }
}