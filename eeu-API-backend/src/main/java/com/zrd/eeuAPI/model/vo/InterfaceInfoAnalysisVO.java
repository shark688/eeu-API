package com.zrd.eeuAPI.model.vo;

import com.zrd.common.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口信息封装视图
 */
@Data
public class InterfaceInfoAnalysisVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionID = 1L;
}
