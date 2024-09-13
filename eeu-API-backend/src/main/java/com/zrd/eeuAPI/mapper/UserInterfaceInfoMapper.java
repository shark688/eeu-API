package com.zrd.eeuAPI.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrd.common.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 张瑞东
* @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Mapper
* @createDate 2024-09-04 16:34:51
* @Entity generator.domain.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {
    //select interfaceInfoId, sum(totalNum) as totalNum from user_interface_info grouped by interfaceInfoId order by totalNum desc limit 3
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




