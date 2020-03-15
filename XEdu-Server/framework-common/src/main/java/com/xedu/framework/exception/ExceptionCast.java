package com.xedu.framework.exception;

import com.xedu.framework.model.response.ResultCode;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 10:36.
 * @Description: 异常抛出类
 */
public class ExceptionCast {
    public static void cast(ResultCode resultCode){
        // 抛出自定义的异常
        throw new CustomException(resultCode);
    }
}
