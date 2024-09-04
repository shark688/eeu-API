package com.zrd.eeuAPI.model.dto.userInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @author zrd
 *
 */
@Data
public class UserInterfaceInfoAddRequest implements Serializable {

    /**
     * 接口用户id
     */
    private Long userId;

    /**
     * 接口id
     */
    private Long interfaceInfoId;

    /**
     * 总共调用次数
     */
    private Long totalCalls;

    /**
     * 剩余调用次数
     */
    private Long leftCalls;

    private static final long serialVersionUID = 1L;
}