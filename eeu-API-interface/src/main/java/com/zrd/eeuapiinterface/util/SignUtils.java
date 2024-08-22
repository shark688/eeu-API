package com.zrd.eeuapiinterface.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.Map;

/**
 * 加密工具
 */
public class SignUtils {
    /**
     * 加密算法
     * @param hashMap
     * @param secretKey
     * @return
     */
    public static String getSign(String body, String secretKey)
    {
        Digester md5 = new Digester(DigestAlgorithm.SHA384);
        String content = body + "." + secretKey;
        String result = md5.digestHex(content);
        return result;
    }

}
