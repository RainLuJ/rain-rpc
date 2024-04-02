package com.rainlu.rpc.core.server.http;

import io.vertx.core.Vertx;

/**
 * 基于Vertx实现的 HTTP 服务器
 */
public class VertxHttpServer implements HttpServer {

    /**
     * 启动 HTTP 服务器
     *
     * @param port
     */
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 监听端口并处理请求
      /*// 简易测试：处理请求
        server.requestHandler(request -> {
            // 处理HTTP请求
            System.out.println("Received request for " + request.uri());

            // 发送HTTP响应
            request.response()
                    .putHeader("Content-Type", "text/plain")
                    .end("Hello from Vert.x!");
        });*/
        server.requestHandler(new HttpServerHandler());

        // 启动 HTTP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.err.println("Failed to start server: " + result.cause());
            }
        });
    }
}
