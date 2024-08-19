package com.zrd.eeuapiinterface.controller;

import com.zrd.eeuapiinterface.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public String getUsernameByPOST(@RequestBody User user)
    {
        return "POST 你的名字是" + user.getUsername();
    }
}
