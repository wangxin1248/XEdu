package com.xedu.api.media;

import com.xedu.framework.domain.media.response.CheckChunkResult;
import com.xedu.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/4 09:15.
 * @Description: 媒资文件上传接口
 */
@Api(value = "负责管理媒体文件的上传分块合并任务",tags = "媒资管理接口")
public interface MediaUploadControllerApi {
    @ApiOperation("文件注册")
    public ResponseResult register(String fileMd5,
                                   String fileName,
                                   Long fileSize,
                                   String mimetype,
                                   String fileExt);
    @ApiOperation("分块校验")
    public CheckChunkResult checkchunk(String fileMd5,
                                       Integer chunk,
                                       Integer chunkSize);
    @ApiOperation("上传分块")
    public ResponseResult uploadchunk(MultipartFile file,
                                      Integer chunk,
                                      String fileMd5);
    @ApiOperation("合并分块")
    public ResponseResult mergechunks(String fileMd5,
                                      String fileName,
                                      Long fileSize,
                                      String mimetype,
                                      String fileExt);
}
