package com.zrd.eeuAPI.model.dto.interfaceInfo;

import lombok.Data;

/**
 * 接口调用请求
 */
@Data
public class InterfaceInfoInvokeRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     */
    private String userRequestParams;
}
