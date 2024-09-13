package com.zrd.eeuapiinterface.controller;

import com.zrd.eeuapisdk.model.User;
import com.zrd.eeuapisdk.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称API
 * @author zrd
 */
@RestController
@RequestMapping("/name")
@Slf4j
public class NameController {

    @GetMapping("/")
    public String getNameByGET(String name)
    {
        return "GET 你的名字是" + name;
    }

    @PostMapping("/")
    public String getNameByPOST(@RequestParam String name)
    {
        return "POST 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPOST(@RequestBody User user, HttpServletRequest httpServletRequest)
    {
//        String accessKey = httpServletRequest.getHeader("accessKey");
//        String nonce = httpServletRequest.getHeader("nonce");
//        String body = httpServletRequest.getHeader("body");
//        String timestamp = httpServletRequest.getHeader("timestamp");
//        String sign = httpServletRequest.getHeader("sign");
//        //TODO 正常应该查询数据库是否有该Key
//        String accessKeyDb = "zrd";
//        String secretKey = "zrd";
//        if(!accessKey.equals(accessKeyDb))
//        {
//            throw new RuntimeException("无权限");
//        }
//
//        if(Long.parseLong(nonce) > 10000)
//        {
//            throw new RuntimeException("无权限");
//        }
//        // 将字符串转换为长整型
//        long timeStamp = Long.parseLong(timestamp);
//
//        // 获取当前时间的时间戳（秒为单位）
//        long currentTime = System.currentTimeMillis() / 1000;
//
//        // 计算五分钟前的时间戳
//        long fiveMinutesAgo = currentTime - 300;
//
//        // 比较时间戳
//        if (timeStamp < fiveMinutesAgo ||  timeStamp > currentTime) {
//            throw new RuntimeException("无权限");
//        }
//
//        if(!SignUtils.getSign(body,secretKey).equals(sign))
//        {
//            throw new RuntimeException("无权限");
//        }
        return "POST 你的名字是" + user.getUsername();
    }
}
