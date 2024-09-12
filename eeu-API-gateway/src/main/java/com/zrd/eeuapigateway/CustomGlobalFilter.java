package com.zrd.eeuapigateway;

import com.zrd.common.model.entity.InterfaceInfo;
import com.zrd.common.model.entity.User;
import com.zrd.common.service.InnerInterfaceInfoService;
import com.zrd.common.service.InnerUserInterfaceInfoService;
import com.zrd.common.service.InnerUserService;
import com.zrd.eeuapisdk.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    /**
     * 全局过滤
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.请求日志
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().value();
        String method = request.getMethodValue().toString();
        log.info("请求唯一标识: " + request.getId());
        log.info("请求路径: " + path);
        log.info("请求方法: " + method);
        log.info("请求参数: " + request.getQueryParams());
        String hostString = request.getLocalAddress().getHostString();
        log.info("请求来源地址: " + hostString);
        log.info("请求来源地址: " + request.getRemoteAddress());
        //2.黑白名单
        if(!IP_WHITE_LIST.contains(hostString))
        {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        //3.用户鉴权(accesskey和secretkey)
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String body = headers.getFirst("body");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        //查询数据库是否有该Key
        User invokeUser = null;
        try{
            invokeUser = innerUserService.getInvokeUser(accessKey);
        }
        catch (Exception e)
        {
            log.error("getInvokeUser error",e);
        }
        if(invokeUser == null)
        {
            return handleNoAuth(response);
        }
        String secretKey = invokeUser.getSecretKey();
//        if(!accessKey.equals(accessKeyDb))
//        {
//            return handleNoAuth(response);
//        }

        if(Long.parseLong(nonce) > 10000)
        {
            return handleNoAuth(response);
        }
        // 将字符串转换为长整型
        long timeStamp = Long.parseLong(timestamp);

        // 获取当前时间的时间戳（秒为单位）
        long currentTime = System.currentTimeMillis() / 1000;

        // 计算五分钟前的时间戳
        long fiveMinutesAgo = currentTime - 300;

        // 比较时间戳
        if (timeStamp < fiveMinutesAgo ||  timeStamp > currentTime) {
            return handleNoAuth(response);
        }

        if(sign == null || !SignUtils.getSign(body,secretKey).equals(sign))
        {
            return handleNoAuth(response);
        }
        //4.请求接口是否存在
        //查询数据库中是否含有对应的url,以及校验请求方法等
        InterfaceInfo invokeInterface = null;
        try{
            invokeInterface = innerInterfaceInfoService.getInvokeInterface(path,method);
        }catch (Exception e)
        {
            log.error("getInvokeInterface error",e);
        }
        if(invokeInterface == null)
        {
            return handleError(response);
        }
        //5.请求转发,调用模拟接口
//        Mono<Void> filter = chain.filter(exchange);

        //6.打印响应日志,调用次数+1
        log.info("响应结果为: " + response.getStatusCode());
        return handleResponse(exchange,chain,invokeUser.getId(),invokeInterface.getId());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono handleNoAuth(ServerHttpResponse response)
    {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    private Mono handleError(ServerHttpResponse response)
    {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long userId,long interfaceId) {
        try {
            // 获取原始的响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if(statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                //调用成功后,调用接口来修改数据库调用次数+1 invokeCount
                                try {
                                    boolean invoke = innerUserInterfaceInfoService.invoke(interfaceId, userId);
                                } catch (Exception e) {
                                   log.error("invoke error",e);
                                }
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                //打印日志
                                log.info("响应结果: "+ data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        }catch (Exception e){
            // 处理异常情况，记录错误日志
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }


}