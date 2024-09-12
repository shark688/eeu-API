package com.zrd.eeuAPI.service.impl.inner;

import com.zrd.common.service.InnerUserInterfaceInfoService;
import com.zrd.eeuAPI.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService

public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 调用次数+1
     * @param interfaceId
     * @param userId
     * @return
     */
    @Override
    public boolean invoke(long interfaceId, long userId) {
        return userInterfaceInfoService.invoke(interfaceId,userId);
    }
}
