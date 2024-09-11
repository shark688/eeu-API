package com.zrd.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.common.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.zrd.common.model.entity.UserInterfaceInfo;


/**
* @author 张瑞东
* @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Service
* @createDate 2024-09-04 16:13:28
*/
public interface InnerUserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验userInterfaceInfo是否有效
     * @param userInterfaceInfo
     * @param add
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 调用接口统计
     * @param interfaceId
     * @param userId
     * @return
     */
    boolean invoke(long interfaceId,long userId);

    /**
     * 查询包装类
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest interfaceInfoQueryRequest);


}
