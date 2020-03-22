package com.xedu.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.CmsSite;
import com.xedu.framework.domain.cms.response.CmsCode;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.manage_cms_client.dao.CmsPageRepository;
import com.xedu.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 11:39.
 * @Description: CmsPage 页面的操作service
 */
@Service
public class PageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageService.class);
    // 注入所需的类
    @Autowired
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    GridFsTemplate gridFsTemplate;

    /**
     * 根据页面id将页面信息保存到服务器物理路径上
     * @param pageId 页面id
     */
    public void savePage2Serverpath(String pageId){
        // 首先根据页面id查找到页面的具体信息
        CmsPage cmsPage = this.getPageById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 从页面对象中获取html文件id
        String htmlFileId = cmsPage.getHtmlFileId();

        // 从GridFs中查询html对象
        InputStream inputStream = this.getFileById(htmlFileId);
        if(inputStream == null){
            LOGGER.error("getFileById InputStream is null,htmlFileId: {}",htmlFileId);
            return;
        }

        // 得到站点对象
        CmsSite cmsSite = this.getSiteById(cmsPage.getSiteId());
        if(cmsSite == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_SITEIDISNULL);
        }

        // 得到站点的物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        // 得到页面的物理路径
        String pagepath = sitePhysicalPath+cmsPage.getPagePhysicalPath()+cmsPage.getPageName();

        // 将html文件保存到服务器物理路径上去
        FileOutputStream fileOutputStream = null;
        try {
            // 将输入流拷贝到指定的文件上去
            fileOutputStream = new FileOutputStream(new File(pagepath));
            IOUtils.copy(inputStream,fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关流
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据文件id从gridFs中获取文件
     * @param fileId 文件id
     * @return 文件流对象
     */
    public InputStream getFileById(String fileId){
        // 查找文件对象
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        // 打开下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        // 定义GridFsResource
        GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
        // 返回文件下载流
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据页面id查询页面对象
     * @param pageId 页面id
     * @return CmsPage对象
     */
    public CmsPage getPageById(String pageId){
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 根据站点id查询站点对象
     * @param siteId 站点id
     * @return 站点对象
     */
    public CmsSite getSiteById(String siteId){
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }
}
