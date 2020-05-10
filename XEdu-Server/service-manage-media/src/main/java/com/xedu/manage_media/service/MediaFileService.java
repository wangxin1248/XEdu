package com.xedu.manage_media.service;

import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.domain.media.MediaFile;
import com.xedu.framework.domain.media.request.QueryMediaFileRequest;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/5/9 20:22.
 * @Description: 媒资文件控制业务实现
 */
@Service
public class MediaFileService {
    @Autowired
    MediaFileRepository mediaFileRepository;
    /**
     * 查询所有的媒资文件列表
     * @param page
     * @param size
     * @param queryMediaFileRequest
     * @return
     */
    public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        // 判断请求对象是否存在
        if(queryMediaFileRequest == null){
            queryMediaFileRequest = new QueryMediaFileRequest();
        }
        // 查询文件对象
        MediaFile mediaFile = new MediaFile();
        // 将查询条件设置到查询文件对象上
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
            mediaFile.setTag(queryMediaFileRequest.getTag());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
            mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
        }
        if(StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
            mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
        }
        // 查询条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("tag", ExampleMatcher.GenericPropertyMatchers.contains())//tag字段模糊匹配
                .withMatcher("fileOriginalName", ExampleMatcher.GenericPropertyMatchers.contains());//文件原始名称模糊匹配
        // processStatus为精确匹配，无需在条件查询器中设置，因为默认就是精确匹配
        // 定义Example实例
        Example<MediaFile> example = Example.of(mediaFile,exampleMatcher);
        // 对分页查询条件进行处理
        if(page <= 0){
            page = 1;
        }
        page = page-1;// page真实数字和前端传入的差一
        if(size <= 0){
            size = 10;
        }
        // 创建分页对象
        Pageable pageable = PageRequest.of(page, size);
        // 查询所有的文件列表
        Page<MediaFile> all = mediaFileRepository.findAll(example, pageable);
        // 将结果封装到QueryResult中
        QueryResult<MediaFile> queryResult = new QueryResult<>();
        queryResult.setTotal(all.getTotalElements());
        queryResult.setList(all.getContent());
        // 返回结果
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
