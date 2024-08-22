package com.zrd.eeuapiinterface;


import com.zrd.eeuapisdk.client.EeuClient;
import com.zrd.eeuapisdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EeuApiInterfaceApplicationTests {

    @Resource
    private EeuClient eeuClient;
    @Test
    void mainTest() {
        String name = eeuClient.getNameByGET("zrd");
        User user = new User();
        user.setUsername("zrd");
        String usernameByPOST = eeuClient.getUsernameByPOST(user);
        System.out.println("result:" + name);
        System.out.println(usernameByPOST);
    }

}
