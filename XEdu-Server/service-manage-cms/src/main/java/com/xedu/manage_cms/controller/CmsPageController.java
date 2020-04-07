package com.xedu.manage_cms.controller;

import com.xedu.api.cms.CmsPageControllerApi;
import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.domain.cms.response.CmsPageResult;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_cms.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 11:41.
 * @Description: 实现页面查询接口
 */
@RestController// 该controller输出json数据
@RequestMapping("/cms/page")// 该controller的访问路径
public class CmsPageController implements CmsPageControllerApi {
    // 调用pageService执行相关查询任务
    @Autowired
    PageService pageService;

    /**
     * 针对get请求的页面请求url查询对于的页面列表
     * @param page 请求的页面编号
     * @param size 请求的页面列表数量
     * @param queryPageRequest 请求查询条件
     * @return QueryResponseResult
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page,@PathVariable("size") int size, QueryPageRequest queryPageRequest) {
        return pageService.findList(page,size,queryPageRequest);
    }

    /**
     * post提交所要新增的页面信息
     * @param page 页面对象json，使用RequestBody注解捕获http body中的内容并封装到 CmsPage对象中
     * @return CmsPageResult
     */
    @Override
    @PostMapping("/add")
    public CmsPageResult addPage(@RequestBody CmsPage page) {
        return pageService.addPage(page);
    }

    /**
     * get请求当前id所对应的cmspage对象
     * @param id cmspage id
     * @return cmspage对象
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findPageById(@PathVariable("id") String id) {
        return pageService.findPageById(id);
    }

    /**
     * 使用put请求更新页面信息，http 方法中 put 表示更新
     * @param id
     * @param page
     * @return
     */
    @Override
    @PutMapping("/update/{id}")
    public CmsPageResult updatePage(@PathVariable("id") String id, @RequestBody CmsPage page) {
        return pageService.updatePage(id,page);
    }

    /**
     * 使用delete请求删除页面
     * @param id
     * @return
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deletePage(@PathVariable("id") String id) {
        return pageService.deletePage(id);
    }

    @Override
    @PostMapping("/post/{id}")
    public ResponseResult postPage(@PathVariable("id") String id) {
        return pageService.postPage(id);
    }

    // 保存页面
    @Override
    @PostMapping("/save")
    public CmsPageResult savePage(@RequestBody CmsPage page) {
        return pageService.save(page);
    }
}
