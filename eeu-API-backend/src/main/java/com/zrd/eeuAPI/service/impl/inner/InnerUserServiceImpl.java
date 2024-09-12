package com.zrd.eeuAPI.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrd.common.model.entity.User;
import com.zrd.common.service.InnerUserService;
import com.zrd.eeuAPI.common.ErrorCode;
import com.zrd.eeuAPI.exception.BusinessException;
import com.zrd.eeuAPI.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据密钥获取用户
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if(StringUtils.isAnyBlank(accessKey))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密钥错误");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }
}
