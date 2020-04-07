package com.xedu.framework.domain.course.request;

import com.xedu.framework.model.response.ResponseResult;
import com.xedu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/7 18:22.
 * @Description: 课程预览效果对象类
 */
@Data
@NoArgsConstructor
@ToString
public class CoursePublishResult extends ResponseResult {
    String previewUrl;
    public CoursePublishResult(ResultCode resultCode, String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
