package com.zrd.common.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zrd.common.model.entity.User;

/**
 * 用户服务
 *
 * @author zrd
 *
 */
public interface InnerUserService{
    /**
     * 根据密钥查询数据库是否有用户存在
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
