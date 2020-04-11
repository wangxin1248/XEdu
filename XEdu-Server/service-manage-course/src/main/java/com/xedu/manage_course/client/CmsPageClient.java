package com.xedu.manage_course.client;

import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.response.CmsPageResult;
import com.xedu.framework.domain.cms.response.CmsPostPageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/4 22:06.
 * @Description: 远程调用cms模块中的cmspage页面对象
 */
@FeignClient(value = "SERVICE-MANAGE-CMS")// 声明所要访问的服务id
public interface CmsPageClient {
    // 获取页面信息
    @GetMapping("/cms/page/get/{id}")// 声明该接口的请求方法
    public CmsPage findById(@PathVariable("id") String id);
    // 保存界面信息
    @PostMapping("/cms/page/save")
    public CmsPageResult savePage(@RequestBody CmsPage page);
    // 发布课程页面
    @PostMapping("/cms/page/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);
}
