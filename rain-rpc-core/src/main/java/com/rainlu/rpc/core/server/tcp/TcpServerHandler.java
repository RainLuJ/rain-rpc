package com.rainlu.rpc.core.server.tcp;

import com.rainlu.rpc.core.model.RpcRequest;
import com.rainlu.rpc.core.model.RpcResponse;
import com.rainlu.rpc.core.protocol.*;
import com.rainlu.rpc.core.registry.local.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * TCP 请求处理器
 */
public class TcpServerHandler implements Handler<NetSocket> {

    /**
     * 处理请求
     *
     * @param socket the event to handle
     */
    @Override
    public void handle(NetSocket socket) {
        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            /* 从Buffer中获取数据 */
            // 接受请求，解码
            ProtocolMessage<RpcRequest> protocolMessage;
            try {
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (IOException e) {
                throw new RuntimeException("协议消息解码错误");
            }
            RpcRequest rpcRequest = protocolMessage.getBody();
            ProtocolMessage.Header header = protocolMessage.getHeader();

            // 处理请求
            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            try {
                // 获取要调用的服务实现类，通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            // 发送响应，编码
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue());
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
            try {
                // 向连接到服务器的客户端发送数据（Buffer是Vert.x提供的字节数组缓冲区实现）
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                socket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException("协议消息编码错误");
            }
        });

        /*
            将 TcpBufferHandlerWrapper 设置成这个 NetSocket 的数据处理器。
            这样一来，只要客户端通过此连接发送任何数据，Vert.x 框架就会自动调用 TcpBufferHandlerWrapper 中的 handle(Buffer buffer) 方法来处理这些数据。

            尽管在代码中没有显式地调用 TcpBufferHandlerWrapper 的 handle() 方法，但通过设置它作为 NetSocket 的数据处理器，
            每次有新的数据到达时，Vert.x 异步事件模型会自动触发该方法的调用。
            这样做的目的是为了让您的应用能够异步地、逐块地处理接收到的TCP数据流，同时确保不会阻塞主线程，符合Vert.x的事件驱动编程模型。
         */
        socket.handler(bufferHandlerWrapper);
    }

}
