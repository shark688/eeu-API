package com.zrd.eeuAPI.service;

import com.zrd.common.service.InnerUserInterfaceInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;
    @Test
    public void invoke() {
        boolean b = innerUserInterfaceInfoService.invoke(23L,1817474220620349442L);
        Assertions.assertTrue(b);
    }
}