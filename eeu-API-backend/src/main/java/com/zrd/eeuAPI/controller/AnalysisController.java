package com.zrd.eeuAPI.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrd.common.model.entity.InterfaceInfo;
import com.zrd.common.model.entity.UserInterfaceInfo;
import com.zrd.eeuAPI.annotation.AuthCheck;
import com.zrd.eeuAPI.common.BaseResponse;
import com.zrd.eeuAPI.common.ErrorCode;
import com.zrd.eeuAPI.common.ResultUtils;
import com.zrd.eeuAPI.exception.BusinessException;
import com.zrd.eeuAPI.mapper.UserInterfaceInfoMapper;
import com.zrd.eeuAPI.model.vo.InterfaceInfoAnalysisVO;
import com.zrd.eeuAPI.model.vo.InterfaceInfoVO;
import com.zrd.eeuAPI.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;


    @GetMapping("/interface/top/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoAnalysisVO>> listTopInvokeInterfaceInfo()
    {
        //1.校验limit是否合法

        //2.查询接口调用的结果
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(3);
        if(CollectionUtil.isEmpty(userInterfaceInfos))
        {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库异常");
        }
        //3.根据接口id查询对应的实体
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfos.stream().collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));
        //4.封装到VO当中
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.list(queryWrapper);
        if(CollectionUtil.isEmpty(interfaceInfos))
        {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据库异常");
        }
        List<InterfaceInfoAnalysisVO> interfaceInfoAnalysisVOList = interfaceInfos.stream().map(interfaceInfo -> {
            InterfaceInfoAnalysisVO interfaceInfoAnalysisVO = new InterfaceInfoAnalysisVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoAnalysisVO);
            int totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalCalls().intValue();
            interfaceInfoAnalysisVO.setTotalNum(totalNum);
            return interfaceInfoAnalysisVO;
        }).collect(Collectors.toList());
        //5.返回
        return ResultUtils.success(interfaceInfoAnalysisVOList);
    }
}
