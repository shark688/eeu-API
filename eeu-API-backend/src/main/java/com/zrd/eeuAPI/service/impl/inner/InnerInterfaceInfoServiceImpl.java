package com.zrd.eeuAPI.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrd.common.model.entity.InterfaceInfo;
import com.zrd.common.model.entity.User;
import com.zrd.common.service.InnerInterfaceInfoService;
import com.zrd.eeuAPI.common.ErrorCode;
import com.zrd.eeuAPI.exception.BusinessException;
import com.zrd.eeuAPI.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    /**
     * 判断接口是否存在
     * @param url
     * @param method
     * @return
     */
    @Override
    public InterfaceInfo getInvokeInterface(String url, String method) {
        if(StringUtils.isAnyBlank(url,method))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口不存在");
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        return interfaceInfo;
    }
}
