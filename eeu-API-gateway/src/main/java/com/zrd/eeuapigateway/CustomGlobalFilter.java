package com.zrd.eeuapigateway;

import com.zrd.eeuapisdk.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

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
        log.info("请求唯一标识: " + request.getId());
        log.info("请求路径: " + request.getPath().value());
        log.info("请求方法: " + request.getMethodValue());
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
        //TODO 正常应该查询数据库是否有该Key
        String accessKeyDb = "zrd";
        String secretKey = "zrd";
        if(!accessKey.equals(accessKeyDb))
        {
            return handleNoAuth(response);
        }

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

        if(!SignUtils.getSign(body,secretKey).equals(sign))
        {
            return handleNoAuth(response);
        }
        //4.请求接口是否存在
        //TODO 查询数据库中是否含有对应的url,以及校验请求方法等

        //5.请求转发,调用模拟接口
        Mono<Void> filter = chain.filter(exchange);

        //6.打印响应日志,调用次数+1
        log.info("响应结果为: " + response.getStatusCode());
        //TODO 同样使用接口

        //7.校验响应结果
        if(response.getStatusCode() == HttpStatus.OK)
        {

        }
        else {
            //8.调用失败
            return handleError(response);
        }
        return filter;
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
}