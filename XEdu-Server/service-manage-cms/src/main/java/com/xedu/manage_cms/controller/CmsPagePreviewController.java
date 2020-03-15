package com.xedu.manage_cms.controller;

import com.xedu.framework.web.BaseController;
import com.xedu.manage_cms.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/15 23:49.
 * @Description: 进行页面预览
 */
@Controller
public class CmsPagePreviewController extends BaseController {
    @Autowired
    PageService pageService;

    //接收到页面id
    @RequestMapping(value="/cms/preview/{pageId}",method = RequestMethod.GET)
    public void preview(@PathVariable("pageId") String pageId){
        // 获取页面信息
        String html = pageService.getPageHtml(pageId);
        if(StringUtils.isNoneEmpty(html)){
            try {
                // 获取页面输出流
                ServletOutputStream outputStream = response.getOutputStream();
                // 直接将页面以utf-8编码写入到浏览器上
                outputStream.write(html.getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
