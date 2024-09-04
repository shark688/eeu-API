package com.zrd.eeuAPI.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrd.eeuAPI.common.ErrorCode;
import com.zrd.eeuAPI.exception.BusinessException;
import com.zrd.eeuAPI.exception.ThrowUtils;
import com.zrd.eeuAPI.mapper.UserInterfaceInfoMapper;
import com.zrd.eeuAPI.model.dto.userInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.zrd.eeuAPI.model.entity.UserInterfaceInfo;
import com.zrd.eeuAPI.service.UserInterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 张瑞东
* @description 针对表【user_interface_info(用户接口调用关系表)】的数据库操作Service实现
* @createDate 2024-09-04 16:13:28
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，参数不能为空
        if (add) {
            if(userInterfaceInfo.getInterfaceInfoId()<=0 || userInterfaceInfo.getUserId()<=0)
            {
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口或用户不存在");
            }
        }
       if(userInterfaceInfo.getLeftCalls() < 0)
       {
           throw new BusinessException(ErrorCode.PARAMS_ERROR,"剩余次数不能修小于0");
       }


    }

    /**
     * 接口调用统计
     * @param interfaceId
     * @param userId
     * @return
     */
    @Override
    public boolean invoke(long interfaceId, long userId) {
        if(interfaceId <= 0 || userId <= 0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口id或用户id错误");
        }
        //TODO 加锁
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId",interfaceId);
        updateWrapper.eq("userId",userId);
        updateWrapper.gt("leftCalls",0);
        updateWrapper.setSql("leftCalls = leftCalls - 1,totalCalls = totalCalls + 1");

        boolean updateResult = this.update(updateWrapper);
        return updateResult;
    }

    @Override
    public QueryWrapper<UserInterfaceInfo> getQueryWrapper(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest) {
        return null;
    }
}




