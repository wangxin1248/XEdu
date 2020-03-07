package com.xedu.manage_cms.controller;

import com.xedu.api.cms.CmsPageControllerApi;
import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 11:41.
 * @Description: 实现页面查询接口
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController implements CmsPageControllerApi {
    // 调用pageService执行相关查询任务
    @Autowired
    PageService pageService;

    /**
     * 针对get请求的页面请求url查询对于的页面列表
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page,size,queryPageRequest);
    }
}
