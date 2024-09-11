package com.zrd.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.common.model.entity.InterfaceInfo;

/**
* @author 张瑞东
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-07-27 19:57:59
*/
public interface InnerInterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 在数据库中查询接口是否存在
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInvokeInterface(String path,String method);

}
