# Rain-RPC 框架

> 一个基于 [Vertx](https://github.com/vert-x3/vertx) 的 RPC 框架


## 项目介绍

基于 Java + Etcd + Vert.x + 自定义协议实现。开发者可以引入 Spring Boot Starter，通过注解和配置文件快速使用框架，像调用本地方法一样轻松调用远程服务；还支持通过 SPI 机制动态扩展序列化器、负载均衡器、重试和容错策略等。


## 技术选型

### 后端

后端技术以 Java 为主。

- ⭐️ Vert.x 框架
- ⭐️ Etcd 云原生存储中间件（jetcd 客户端）
- ⭐️ SPI 机制
- ⭐️ 多种序列化器
    - JSON 序列化
    - Kryo 序列化
    - Hessian 序列化
- ⭐️ 多种设计模式
    - 双检锁单例模式
    - 工厂模式
    - 代理模式
    - 装饰者模式
- ⭐️ Spring Boot Starter 开发
- 反射和注解驱动
- Guava Retrying 重试库
- JUnit 单元测试
- Logback 日志库
- Hutool、Lombok 工具库

## 源码目录

- rain-rpc-core： RPC 框架核心代码
- rain-rpc-easy： RPC 框架简易版（适合新手入门）
- example-common：示例代码公用模块
- example-consumer：示例服务消费者
- example-provider：示例服务提供者
- example-springboot-consumer：示例服务消费者（Spring Boot 框架）
- example-springboot-provider：示例服务提供者（Spring Boot 框架）
- rain-rpc-spring-boot-starter：注解驱动的 RPC 框架，可在 Spring Boot 项目中快速使用

## 核心架构设计和模块
1. 消费者代理模块：基于代理模式实现，在服务消费者对服务提供者进行远程调用时，不需要考虑是如何实现调用的，直接调用写好的服务接口即可。
2. 注册中心：基于 Etcd 实现注册中心，服务提供者会将自己的地址、接口、分组等详细信息都上报到注册中心模块，并且当服务上线、下线的时候通知到注册中心;服务调用方就可以从注册中心动态获取调用信息，而不用在代码内硬编码调用地址。
3. 本地服务注册器：服务提供端存储服务名称和实现类的映射关系，便于后续根据服务名获取到对应的实现类，从而通过反射完成调用。
4. 路由模块(负载均衡)：当有多个服务和服务提供者节点时，服务消费者需要确认请求哪一个服务和节点。因此我设计了路由模块，通过实现不同算法的负载均衡器，帮助服务消费者选择一个服务节点发起调用。
5. 自定义 RPC 协议:自主实现了基于 Ver.x(TCP)和自定义请求头的网络传输协议，并自主实现了对字节数组的编码/解码器，可以完成性能更高的请求和响应。
6. 请求处理器：服务提供者在接收到请求后，可以通过请求处理器解析请求，从本地服务注册器中找到服务实现类并调用方法。
7. 重试和容错模块：在调用出错时，进行重试、降级、故障转移等操作，保障服务的可用性和稳定性。
8. 启动器模块：基于注解驱动 + Spring Boot Starter，开发者可以快速引入 RPC 框架到项目中。
