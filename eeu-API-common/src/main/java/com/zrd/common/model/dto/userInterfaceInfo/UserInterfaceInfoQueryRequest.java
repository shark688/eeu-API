package com.zrd.common.model.dto.userInterfaceInfo;

import com.zrd.common.commonEntity.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author zrd
 *
 */
@Data
public class UserInterfaceInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

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

    /**
     * 状态（0-禁用，1-允许）
     */
    private Integer status;

}