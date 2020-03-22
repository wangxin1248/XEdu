package com.xedu.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.CmsTemplate;
import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.domain.cms.response.CmsCode;
import com.xedu.framework.domain.cms.response.CmsPageResult;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_cms.config.RabbitmqConfig;
import com.xedu.manage_cms.dao.CmsPageRepository;
import com.xedu.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 14:27.
 * @Description:
 */
@Service
public class PageService {
    @Autowired
    CmsPageRepository cmsPageRepository;// 注入dao对象
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    RestTemplate restTemplate;// 网络获取Template
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    GridFSBucket gridFSBucket;// 从数据库中读取页面模型
    @Autowired
    RabbitTemplate rabbitTemplate;// RabbitMQ模版

    /**
     * 执行分页查询服务
     * @param page 页面编号，从1开始
     * @param size 页面内容大小
     * @param queryPageRequest 查询条件
     * @return 页面列表
     */
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest){
        // 判断使用是否查询条件进行查询
        if(queryPageRequest == null){
            queryPageRequest = new QueryPageRequest();
        }

        // 执行条件查询
        CmsPage cmsPage = new CmsPage();
        // 设置站点id查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getSiteId())){
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        // 设置页面别名查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getPageAlias())){
            cmsPage.setPageAliase(queryPageRequest.getPageAlias());
        }
        // 设置页面名称查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getPageName())){
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        // 设置页面类型查询条件
        if(StringUtils.isNotEmpty(queryPageRequest.getPageType())){
            cmsPage.setPageType(queryPageRequest.getPageType());
        }

        // 查询条件匹配器，设置支持页面别名页面名称模糊查询
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName",ExampleMatcher.GenericPropertyMatchers.contains());
        // 创建查询条件实例
        Example<CmsPage> example = Example.of(cmsPage,matcher);
        // 对分页查询条件进行判断
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
        // 执行查询任务
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        // 将查询结果封装到QueryResult中
        QueryResult<CmsPage> result = new QueryResult<>();
        // 内容
        result.setList(all.getContent());
        // 长度
        result.setTotal(all.getTotalElements());
        // 返回查询响应
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    /**
     * 新增页面实现
     * @param page
     * @return
     */
    public CmsPageResult addPage(CmsPage page){
        // 假如page为空，抛出异常
        if(page == null){
            // 抛出非法异常
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 检验当前所要保存的页面是否存在，按照pageName，siteId以及PageWebPath三者作为唯一索引
        CmsPage page1 = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(page.getPageName(),page.getSiteId(),page.getPageWebPath());
        // 假如页面存在，抛出异常
        if(page1 != null){
            // 抛出页面存在异常
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        // 不存在当前页面则新增页面
        page.setPageId(null);// 让pageid由数据库自动生成
        cmsPageRepository.save(page);
        // 创建返回对象
        CmsPageResult cmsPageResult = new CmsPageResult(CommonCode.SUCCESS,page);
        return cmsPageResult;
    }

    /**
     * 根据id查找页面对象
     * @param id 页面id
     * @return cmspage 页面对象
     */
    public CmsPage findPageById(String id){
        // 根据id查找页面对象
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        // 假如对象存在则返回否则返回null
        return optional.orElse(null);
    }

    /**
     * 更新页面
     * @param id 页面id
     * @param page 所要更新的页面对象
     * @return CmsPageResult
     */
    public CmsPageResult updatePage(String id,CmsPage page){
        // 首先根据id查找到页面对象
        CmsPage page1 = this.findPageById(id);
        // 更新页面对象中的内容
        if(page1 != null){
            // 站点id
            page1.setSiteId(page.getSiteId());
            // 模版id
            page1.setTemplateId(page.getTemplateId());
            // 页面名称
            page1.setPageName(page.getPageName());
            // 页面别名
            page1.setPageAliase(page.getPageAliase());
            // 访问路径
            page1.setPageWebPath(page.getPageWebPath());
            // 物理路径
            page1.setPagePhysicalPath(page.getPagePhysicalPath());
            // dataUrl
            page1.setDataUrl(page.getDataUrl());
            // 执行更新任务，返回更新之后的cmspage对象
            CmsPage page2 = cmsPageRepository.save(page1);
            // 返回结果
            return new CmsPageResult(CommonCode.SUCCESS,page2);
        }
        // 返回失败
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    /**
     * 根据页面id删除页面
     * @param id 页面id
     * @return ResponseResult
     */
    public ResponseResult deletePage(String id){
        // 首先查找该id对应的页面是否存在
        Optional<CmsPage> optional = cmsPageRepository.findById(id);
        if(optional.isPresent()){
            // 页面存在则执行删除任务
            cmsPageRepository.delete(optional.get());
            return new ResponseResult(CommonCode.SUCCESS);
        }
        // 页面不存在删除失败
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 执行页面静态化
     * @param id 页面id
     * @return 页面静态化字符串
     * 首先获取页面对象
     * 然后获取页面对象对应的模型数据
     * 然后获取页面对象对应的页面模型
     * 最后执行静态化
     */
    public String getPageHtml(String id){
        // 获取页面对应的模型数据
        Map model = getModelById(id);
        if(model == null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        // 获取页面对象对应的页面模型
        String template = getTemplateById(id);
        if(StringUtils.isEmpty(template)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // 执行页面静态化
        String html = generateHtml(template,model);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    private String generateHtml(String template,Map model){
        // 定义配置类，传入Configuration的版本
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 定义模版加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",template);
        // 设置模版加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        try {
            // 获取模版
            Template template1 = configuration.getTemplate("template");
            // 将模版转为字符串
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template1,model);
            return html;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取页面对象对应的页面模型
     * @param id 页面id
     * @return 页面模型
     */
    private String getTemplateById(String id){
        // 首先获取页面对象
        CmsPage page = findPageById(id);
        // 页面不存在抛出异常
        if(page == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 获取页面的模版id
        String templateId = page.getTemplateId();
        if(StringUtils.isEmpty(templateId)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // 根据模版id来获取模版的文件id
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if(optional.isPresent()){
            CmsTemplate cmsTemplate = optional.get();
            // 获取模版文件id
            String templateFileId = cmsTemplate.getTemplateFileId();
            // 模版文件不存在抛出异常
            if(StringUtils.isEmpty(templateFileId)){
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_FILEIDISNULL);
            }
            // 根据id查询文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            // 打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 创建gridFsResource，用于获取流对象
            GridFsResource gridFsResource = new GridFsResource(gridFSFile,gridFSDownloadStream);
            // 获取流中的数据
            try {
                String res = IOUtils.toString(gridFSDownloadStream,"utf-8");
                return res;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取页面对应的模型数据
     * @param id 页面id
     * @return 页面对应的模型数据
     */
    private Map getModelById(String id){
        // 首先获取页面对象
        CmsPage page = findPageById(id);
        // 页面不存在抛出异常
        if(page == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 获取页面的dataurl
        String dataUrl = page.getDataUrl();
        // dataurl为null抛出异常
        if(StringUtils.isEmpty(dataUrl)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        // 根据dateurl获取data数据
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(dataUrl, Map.class);
        Map map = responseEntity.getBody();
        return map;
    }

    /**
     * 发布页面实现方法
     * @param id 页面id
     * @return 操作结果
     * 1。执行静态化
     * 2。保存静态化页面
     * 3。发送消息
     */
    public ResponseResult postPage(String id){
        // 执行页面静态化
        String html = this.getPageHtml(id);
        if(StringUtils.isEmpty(html)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        // 保存静态化页面
        CmsPage cmsPage = this.savePage(id,html);
        // 发送消息
        this.sentPageMessage(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 将已经成功保存的页面信息发送到MQ
     * @param pageId 页面id
     * 1。查找到指定的cmspage对象
     * 2。创建要发送的json消息
     * 3。发送消息
     */
    public void sentPageMessage(String pageId){
        // 查找cmspage对象
        CmsPage cmsPage = this.findPageById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 创建要发送的json消息
        Map<String,String> msgMap = new HashMap<>();
        msgMap.put("pageId",pageId);
        String message = JSON.toJSONString(msgMap);
        // 站点id作为routingKey
        String routingKey = cmsPage.getSiteId();
        // 发送消息
        rabbitTemplate.convertAndSend(RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE,routingKey,message);
    }

    /**
     * 保存html页面
     * @param pageId 页面id
     * @param html 页面信息
     * @return CmsPage对象
     * 1。查找到cmspage对象
     * 2。获取cmspage对象的htmlFileId
     * 3。将cmspage对象所对应的旧的htmlFile进行删除
     * 3。将新的html文件保存到gridFS中
     * 4。更新cmspage对象
     * 5。返回cmspage对象
     */
    public CmsPage savePage(String pageId,String html){
        // 查找cmspage对象
        CmsPage cmsPage = this.findPageById(pageId);
        if(cmsPage == null){
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 将旧的文件进行删除
        String htmlFileId = cmsPage.getHtmlFileId();
        if(StringUtils.isNotEmpty(htmlFileId)){
            gridFsTemplate.delete(Query.query(Criteria.where("_id").is(htmlFileId)));
        }
        // 保存html文件到gridfs
        InputStream inputStream = IOUtils.toInputStream(html);
        ObjectId objectId = gridFsTemplate.store(inputStream,cmsPage.getPageName());
        // 获取html文件id
        String fileId = objectId.toString();
        // 将cmspage中的文件id进行更新
        cmsPage.setHtmlFileId(fileId);
        cmsPageRepository.save(cmsPage);
        return cmsPage;
    }
}
