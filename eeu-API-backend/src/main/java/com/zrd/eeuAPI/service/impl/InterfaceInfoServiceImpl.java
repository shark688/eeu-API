package com.zrd.eeuAPI.service.impl;
import java.util.Date;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zrd.eeuAPI.common.ErrorCode;
import com.zrd.eeuAPI.constant.CommonConstant;
import com.zrd.eeuAPI.exception.BusinessException;
import com.zrd.eeuAPI.exception.ThrowUtils;
import com.zrd.eeuAPI.mapper.InterfaceInfoMapper;
import com.zrd.eeuAPI.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.zrd.eeuAPI.model.entity.*;
import com.zrd.eeuAPI.model.vo.InterfaceInfoVO;
import com.zrd.eeuAPI.model.vo.PostVO;
import com.zrd.eeuAPI.model.vo.UserVO;
import com.zrd.eeuAPI.service.InterfaceInfoService;
import com.zrd.eeuAPI.service.UserService;
import com.zrd.eeuAPI.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* @author 张瑞东
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2024-07-27 19:57:59
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Resource
    private UserService userService;
    /**
     * 参数校验
     * @param interfaceInfo
     * @param add
     */
    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String requestHeader = interfaceInfo.getRequestHeader();
        String responseHeader = interfaceInfo.getResponseHeader();
        String method = interfaceInfo.getMethod();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, url, requestHeader,responseHeader,method), ErrorCode.PARAMS_ERROR,"缺少参数");
        }
        // 有参数则校验
        if(StringUtils.isNotBlank(name) && name.length() > 20)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口名过长");
        }
        if(StringUtils.isNotBlank(description) && description.length() > 300)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口描述过长");
        }
        if(StringUtils.isNotBlank(url) && url.length() > 40)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口url过长");
        }
        //TODO: 接口url格式问题
        //String pattern = "^(?:http|https)://(?:localhost(?::\\d+)?|[-a-zA-Z0-9.]+\\.)+[a-zA-Z]{2,}(?:/[^/#?]+)*\\??(?:[^#]*#?)?$";
        if(false)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"接口url格式不合法");
        }
        //TODO 如何校验请求头和响应头
        List<String> methodList = new ArrayList<>();
        methodList.add("POST");
        methodList.add("GET");
        methodList.add("PUT");
        methodList.add("DELETE");
        if(StringUtils.isNotBlank(method) && !methodList.contains(method))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求类型不合法");
        }
    }


    /**
     * 获取查询包装类
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryRequest == null) {
            return queryWrapper;
        }
        String searchText = interfaceInfoQueryRequest.getSearchText();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        Long id = interfaceInfoQueryRequest.getId();
        String name = interfaceInfoQueryRequest.getName();
        String description = interfaceInfoQueryRequest.getDescription();
        String url = interfaceInfoQueryRequest.getUrl();
        String method = interfaceInfoQueryRequest.getMethod();
        Long userId = interfaceInfoQueryRequest.getUserId();
                // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.and(qw -> qw.like("name", searchText).or().like("description", searchText));
        }
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.like(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


}




