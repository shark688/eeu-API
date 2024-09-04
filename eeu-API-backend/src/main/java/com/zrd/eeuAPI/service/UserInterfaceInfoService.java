package com.zrd.eeuAPI.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.eeuAPI.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.zrd.eeuAPI.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.zrd.eeuAPI.model.entity.InterfaceInfo;
import com.zrd.eeuAPI.model.entity.UserInterfaceInfo;
import com.zrd.eeuAPI.model.vo.UserInterfaceInfoVO;


/**
* @author 张瑞东
* @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Service
* @createDate 2024-09-04 16:13:28
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 参数校验
     * @param userInterfaceInfo
     * @param b
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);

    /**
     * 调用接口统计
     * @param interfaceId
     * @param userId
     * @return
     */
    boolean invoke(long interfaceId,long userId);

    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

}
