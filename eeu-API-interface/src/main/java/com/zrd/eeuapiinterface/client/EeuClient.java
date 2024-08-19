package com.zrd.eeuapiinterface.client;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zrd.eeuapiinterface.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

/**
 * 调用第三方服务接口的方法
 *
 * @author zrd
 */
public class EeuClient {

    public String getNameByGET(String name)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPOST(String name)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getUsernameByPOST(User user)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post("http://localhost:8123/api/name/user/")
                .body(json)
                .execute().body();
        System.out.println(result);
        return result;
    }
}
