package com.xedu.framework.exception;

import com.xedu.framework.model.response.ResultCode;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 10:33.
 * @Description: 自定义异常类，处理项目中的全局异常，基础RuntimeException的
 * 原因在于其的代码不入侵性质
 */
public class CustomException extends RuntimeException{
    // 异常代码
    private ResultCode resultCode;
    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;
    }
    // 获取当前的异常代码
    public ResultCode getResultCode(){
        return resultCode;
    }
}
