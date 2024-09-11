package com.zrd.common.model.vo;

import com.zrd.common.model.entity.UserInterfaceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * 接口视图
 *
 * @author zrd
 *
 */
@Data
public class UserInterfaceInfoVO implements Serializable {
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

    /**
     * 包装类转对象
     *
     * @param userInterfaceInfoVO
     * @return
     */
    public static UserInterfaceInfo voToObj(UserInterfaceInfoVO userInterfaceInfoVO) {
        if (userInterfaceInfoVO == null) {
            return null;
        }
        UserInterfaceInfo interfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoVO, interfaceInfo);
        return interfaceInfo;
    }

    /**
     * 对象转包装类
     *
     * @param userInterfaceInfo
     * @return
     */
    public static UserInterfaceInfoVO objToVo(UserInterfaceInfo userInterfaceInfo) {
        if (userInterfaceInfo == null) {
            return null;
        }
        UserInterfaceInfoVO userInterfaceInfoVO = new UserInterfaceInfoVO();
        BeanUtils.copyProperties(userInterfaceInfo, userInterfaceInfoVO);
        return userInterfaceInfoVO;
    }
}
