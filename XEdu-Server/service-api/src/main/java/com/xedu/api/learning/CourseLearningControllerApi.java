package com.xedu.api.learning;

import com.xedu.framework.domain.learning.response.GetMediaResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/16 15:30.
 * @Description: 学习中心服务对应的学习管理的api接口
 */
@Api(value = "学习管理接口，提供学习中心所需的Api",tags = {"学习服务接口"})
public interface CourseLearningControllerApi {
    @ApiOperation("获取课程学习地址")
    public GetMediaResult getMedia(String courseId, String teachplanId);
}
