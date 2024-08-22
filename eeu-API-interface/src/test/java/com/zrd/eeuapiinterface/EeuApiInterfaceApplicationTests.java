package com.zrd.eeuapiinterface;

import com.zrd.eeuapiinterface.client.EeuClient;
import com.zrd.eeuapiinterface.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EeuApiInterfaceApplicationTests {

    @Test
    void mainTest() {
        String accessKey = "zrd";
        String secretKey = "zrd";
        EeuClient eeuClient = new EeuClient(accessKey,secretKey);
        User user = new User();
        user.setUsername("zrd");
        String result = eeuClient.getUsernameByPOST(user);
        System.out.println(result);
    }

}
