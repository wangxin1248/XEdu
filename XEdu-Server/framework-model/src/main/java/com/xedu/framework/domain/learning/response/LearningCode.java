package com.xedu.framework.domain.learning.response;

import com.xedu.framework.model.response.ResultCode;
import lombok.ToString;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 18:27.
 * @Description: 学习服务对应的返回代码
 */
@ToString
public enum LearningCode  implements ResultCode {
    LEARNING_GETMEDIA_ERROR(false,23001,"媒资文件链接不存在"),
    CHOOSECOURSE_USERISNULL(false,23002,"选课用户不存在"),
    CHOOSECOURSE_TASKISNULL(false,23003,"选课任务不存在");
    //操作代码
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private LearningCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }
}
