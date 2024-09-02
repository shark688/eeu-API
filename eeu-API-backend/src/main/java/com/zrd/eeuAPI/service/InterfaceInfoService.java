package com.zrd.eeuAPI.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.eeuAPI.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.zrd.eeuAPI.model.entity.InterfaceInfo;

/**
* @author 张瑞东
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-07-27 19:57:59
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);


}
