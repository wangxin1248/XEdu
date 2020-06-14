package com.xedu.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 10:39.
 * @Description: 异常捕获类
 */
// 该注解使得该类具有全局异常捕获的能力
@ControllerAdvice
public class ExceptionCatch {
    // log记录
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    // 使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    // 使用builder来构建一个异常类型和错误代码的异常
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    static{
        //在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    // 该注解用来捕获自定义异常
    @ExceptionHandler(CustomException.class)
    // 该注解将结果作为请求响应返回
    @ResponseBody
    public ResponseResult customException(CustomException exception){
        // 记录日志
        LOGGER.error("catch exception : {}\r\nexception: ",exception.getMessage(), exception);
        // 获取当前异常代码
        ResultCode resultCode = exception.getResultCode();
        // 将异常代码封装到ResponseResult并返回
        ResponseResult responseResult = new ResponseResult(resultCode);
        return responseResult;
    }

    // 用来捕获未定义异常，也就是Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        // 记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());

        // 首先创建ImmutableMap来存放所有可处理的异常
        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }
        // 首先在ImmutableMap中查找当前的exception是否已经进行定义了
        final ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        // 定义返回对象
        final ResponseResult responseResult;
        // 假如当前异常已经定义则返回对应结果，否则返回99999
        if (resultCode != null) {
            responseResult = new ResponseResult(resultCode);
        } else {
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }
}
