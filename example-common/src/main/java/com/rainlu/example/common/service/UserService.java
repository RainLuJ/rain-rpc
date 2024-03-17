package com.rainlu.example.common.service;

import com.rainlu.example.common.model.User;

/**
 * 用户服务
 */
public interface UserService {

    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);

    /**
     * 测试Mock是否生效（消费者调用Mock消费者则返回默认值0）
     */
    default Integer getAge() {
        return 666;
    }
}
