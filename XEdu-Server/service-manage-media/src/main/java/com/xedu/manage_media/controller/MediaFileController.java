package com.xedu.manage_media.controller;

import com.xedu.api.media.MediaFileControllerApi;
import com.xedu.framework.domain.media.MediaFile;
import com.xedu.framework.domain.media.request.QueryMediaFileRequest;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.manage_media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/9 20:21.
 * @Description: 媒资文件控制
 */
@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {
    // 注入service
    @Autowired
    MediaFileService mediaFileService;
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<MediaFile> findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryMediaFileRequest queryMediaFileRequest) {
        return mediaFileService.findList(page,size,queryMediaFileRequest);
    }
}
