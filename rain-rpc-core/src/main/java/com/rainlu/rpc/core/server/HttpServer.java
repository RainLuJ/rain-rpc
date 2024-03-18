package com.rainlu.rpc.core.server;

/**
 * HTTP 服务器设计所遵循的接口规范
 */
public interface HttpServer {

    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
