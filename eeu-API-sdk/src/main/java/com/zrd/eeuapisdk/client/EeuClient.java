package com.zrd.eeuapisdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zrd.eeuapisdk.model.User;
import com.zrd.eeuapisdk.util.SignUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用第三方服务接口的方法
 *
 * @author zrd
 */
public class EeuClient {

    private String accessKey;

    private String secretKey;

    public static final String EEU_API_GATEWAY_URL = "http://localhost:8090";

    public EeuClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGET(String name)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.get(EEU_API_GATEWAY_URL + "/api/name", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPOST(String name)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("name", name);

        String result= HttpUtil.post(EEU_API_GATEWAY_URL + "/api/name", paramMap);
        System.out.println(result);
        return result;
    }

    public String getUsernameByPOST(User user)
    {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        String json = JSONUtil.toJsonStr(user);
        String result = HttpRequest.post(EEU_API_GATEWAY_URL+"/api/name/user")
                .addHeaders(getHeaderMap(json))
                .charset(StandardCharsets.UTF_8)
                .body(json)
                .execute().body();
        System.out.println(result);
        return result;
    }



    /**
     * 创建请求体
     * @param body
     * @return
     */
    private Map<String,String> getHeaderMap(String body)
    {
        Map<String,String> map = new HashMap();
        map.put("accessKey",accessKey);
        //绝对不能将secretKey暴露在请求中
        //map.put("secretKey",secretKey);
        map.put("nonce", RandomUtil.randomNumbers(4));
        map.put("body",body);
        map.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        map.put("sign", SignUtils.getSign(body,secretKey));
        return map;
    }

}
