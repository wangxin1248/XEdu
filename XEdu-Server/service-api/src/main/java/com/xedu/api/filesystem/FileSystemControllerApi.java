package com.xedu.api.filesystem;

import com.xedu.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/29 20:27.
 * @Description: 文件系统对应接口
 */
@Api(value = "文件系统接口，提供图片文件的增删改查操作",tags = {"文件系统接口"})
public interface FileSystemControllerApi {

    @ApiOperation("上传文件")
    public UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata);
}
