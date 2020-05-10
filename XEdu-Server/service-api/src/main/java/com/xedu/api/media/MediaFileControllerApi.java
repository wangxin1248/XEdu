package com.xedu.api.media;

import com.xedu.framework.domain.media.MediaFile;
import com.xedu.framework.domain.media.request.QueryMediaFileRequest;
import com.xedu.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/9 20:05.
 * @Description: 媒资文件控制接口
 */
@Api(value = "负责管理媒体文件的增删改查任务",tags = "媒资文件控制接口")
public interface MediaFileControllerApi {
    @ApiOperation("获取媒资文件列表")
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);
}
