package com.xedu.manage_cms.service;

import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.manage_cms.dao.CmsPageRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 14:27.
 * @Description:
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepositoty cmsPageRepositoty;// 注入dao对象

    /**
     * 执行分页查询服务
     * @param page 页面编号，从1开始
     * @param size 页面内容大小
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        // 对查询条件进行判读
        if(page <= 0){
            page = 1;
        }
        if(size <= 0){
            size = 10;
        }
        // 对page进行处理
        page = page-1;

        // 创建分页对象进行分页查询
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepositoty.findAll(pageable);
        // 将查询结果封装到QueryResult中
        QueryResult<CmsPage> result = new QueryResult<>();
        result.setList(all.getContent());
        result.setTotal(all.getTotalPages());
        // 返回查询响应
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }
}
