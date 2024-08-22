package com.zrd.eeuapiinterface.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SignUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zrd.eeuapiinterface.model.User;
import com.zrd.eeuapiinterface.util.SignUtils;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
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

    public EeuClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

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
