package com.xedu.manage_course.exception;

import com.xedu.framework.model.response.CommonCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/6/13 16:07.
 * @Description:
 */
@ControllerAdvice// 该注解使得该类具有全局异常捕获的能力
public class CustomExceptionCatch extends com.xedu.framework.exception.ExceptionCatch {
    static{
        // 在这里加入一些基础的异常类型判断
        builder.put(AccessDeniedException.class, CommonCode.UNAUTHORISE);
    }
}
