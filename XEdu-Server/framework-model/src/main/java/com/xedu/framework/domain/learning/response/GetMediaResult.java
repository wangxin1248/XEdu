package com.xedu.framework.domain.learning.response;

import com.xedu.framework.model.response.ResponseResult;
import com.xedu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 15:24.
 * @Description: 学习服务获取课程计划对应的视频url的响应对象
 */
@Data
@ToString
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {
    /**
     * 课程计划对应的视频url
     */
    private String fileUrl;

    /**
     * 通过构造函数方式保存视频的url
     * @param resultCode 响应结构编码
     * @param fileUrl 视频的url
     */
    public GetMediaResult(ResultCode resultCode, String fileUrl){
        super(resultCode);
        this.fileUrl = fileUrl;
    }
}
