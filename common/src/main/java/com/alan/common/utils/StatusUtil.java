package com.alan.common.utils;

import com.alan.common.constant.StatusConst;
import com.alan.common.enums.ResultEnum;
import com.alan.common.enums.StatusEnum;
import com.alan.common.exception.ResultException;

/**
 * 数据状态工具
 * @author cxxwl96@sina.com
 * @date 2020/2/19
 */
public class StatusUtil {

    /** 逻辑删除语句 */
    public static final String SLICE_DELETE = " set status=" + StatusConst.DELETE + " WHERE id=?";

    /** 不等于逻辑删除条件语句 */
    public static final String NOT_DELETE = "status != " + StatusConst.DELETE;

    /**
     * 获取状态StatusEnum对象
     * @param param 状态字符参数
     */
    public static StatusEnum getStatusEnum(String param){
        try {
            return StatusEnum.valueOf(param.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResultException(ResultEnum.STATUS_ERROR);
        }
    }
}
