package com.zrd.eeuAPI.model.dto.userInterfaceInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zrd.eeuAPI.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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