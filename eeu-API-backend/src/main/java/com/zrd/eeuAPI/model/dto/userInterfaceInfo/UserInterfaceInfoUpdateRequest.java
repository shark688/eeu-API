package com.zrd.eeuAPI.model.dto.userInterfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author zrd
 *
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 总共调用次数
     */
    private Long totalCalls;

    /**
     * 剩余调用次数
     */
    private Long leftCalls;

    /**
     * 状态（0-禁用，1-允许）
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}