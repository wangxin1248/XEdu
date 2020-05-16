package com.xedu.manage_course.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.response.CmsPageResult;
import com.xedu.framework.domain.cms.response.CmsPostPageResult;
import com.xedu.framework.domain.course.*;
import com.xedu.framework.domain.course.ext.CourseInfo;
import com.xedu.framework.domain.course.ext.CourseView;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.domain.course.request.CourseListRequest;
import com.xedu.framework.domain.course.request.CoursePublishResult;
import com.xedu.framework.domain.course.response.AddCourseResult;
import com.xedu.framework.domain.course.response.CourseCode;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_course.client.CmsPageClient;
import com.xedu.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.remote.rmi._RMIConnection_Stub;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 22:06.
 * @Description: 课程管理对应的service
 */
@Service
public class CourseService {
    // 注入对应的dao mapper
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanRepository teachplanRepository;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMarketRepository courseMarketRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CoursepicRepository coursepicRepository;
    @Autowired
    CmsPageClient cmsPageClient;
    @Autowired
    CoursePubRepository coursePubRepository;
    @Autowired
    TeachplanMediaRepository teachplanMediaRepository;
    @Autowired
    TeachplanMediaPubRepository teachplanMediaPubRepository;
    // 注入配置文件信息
    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;

    /**
     * 根据课程id查找课程对应的课程计划
     * @param courseId 课程id
     * @return 课程计划树形结构
     */
    public TeachplanNode findTeachplanList(String courseId){
        return teachplanMapper.selectList(courseId);
    }

    /**
     * 添加课程计划
     * @param teachplan 从前端传入的课程计划对象，有前端json转换而来
     * @return 是否操作成功
     */
    @Transactional
    public ResponseResult addTeachPlan(Teachplan teachplan){
        // 校验课程id和课程计划名称是否合法
        if(teachplan == null
                || StringUtils.isEmpty(teachplan.getCourseid())
                || StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 取出课程id和父节点id
        String courseId = teachplan.getCourseid();
        String parentId = teachplan.getParentid();
        // 判断父节点id是否合法
        if(StringUtils.isEmpty(parentId)){
            // 父节点id为空则根根据课程id获取当前课程的根节点，根节点不存在则创建一个根节点，然后返回根节点的id
            parentId = getTeachplanRoot(courseId);
        }
        // 根据父节点id获取父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentId);
        // 父节点不存在抛出异常
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Teachplan parentTeachPlan = optional.get();
        // 获取父节点信息
        String parentGrade = parentTeachPlan.getGrade();
        // 将所要保存的课程信息进行保存到对象中
        teachplan.setParentid(parentId);// 父节点id
        teachplan.setStatus("0");// 未发布
        if(parentGrade.equals("1")){// 根据父节点级别设置该课程计划的级别
            teachplan.setGrade("2");
        }else{
            teachplan.setGrade("3");
        }
        teachplan.setCourseid(parentTeachPlan.getCourseid());
        // 将对象进行保存
        teachplanRepository.save(teachplan);
        // 返回操作成功代码
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据课程id获取当前课程的根节点
     * @param courseId 课程id
     * @return 根节点id
     */
    private String getTeachplanRoot(String courseId) {
        // 校验课程id
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }
        // 根据课程id获取课程信息
        CourseBase courseBase = optional.get();
        // 查找根节点
        List<Teachplan> res = teachplanRepository.findByCourseidAndParentid(courseId,"0");
        // 判断根节点是否存在
        if(res == null || res.size() == 0){
            // 根节点不存在则创建一个根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setCourseid(courseId);// 课程计划id
            teachplan.setPname(courseBase.getName());// 课程计划名称和课程名称一致
            teachplan.setParentid("0");// 课程父节点，因为是新节点所以是0
            teachplan.setGrade("1");// 级别，一级目录
            teachplan.setStatus("0");// 发布状态，未发布
            // 保存该节点
            teachplanRepository.save(teachplan);
            // 返回该节点对应的id
            return teachplan.getId();
        }
        // 根节点存在则直接返回该节点的id
        Teachplan teachplan = res.get(0);
        return teachplan.getId();
    }

    /**
     * 根据请求对象的信息以及分页信息查询课程列表
     * @param courseListRequest 查询条件
     * @return 课程分页对象
     */
    public QueryResponseResult<CourseInfo> findCourseListPage(int page, int size, CourseListRequest courseListRequest){
        if(courseListRequest == null){
            // 查询条件为null则默认为空
            courseListRequest = new CourseListRequest();
        }
        // 对page和size进行合法性测验
        if(page<=0){
            page = 1;
        }
        if(size<=0){
            size = 20;
        }
        // 设置分页查询条件
        PageHelper.startPage(page,size);
        // 执行查询操作
        Page<CourseInfo> courseInfoList = courseMapper.findCourseListPage(courseListRequest);
        // 获取课程列表
        List<CourseInfo> res = courseInfoList.getResult();
        // 构造返回结果
        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setTotal(courseInfoList.getTotal());
        queryResult.setList(res);
        QueryResponseResult responseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        return responseResult;
    }

    /**
     * 新增课程
     * @param courseBase 新增的课程对象
     * @return ResponseResult
     */
    @Transactional
    public AddCourseResult addCourse(CourseBase courseBase){
        if(courseBase == null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //课程状态默认为未发布
        courseBase.setStatus("202001");
        // 调用save进行对象的保存
        courseBaseRepository.save(courseBase);
        // TODO:courseBase.getId()并没有返回对应的id
        return new AddCourseResult(CommonCode.SUCCESS,courseBase.getId());
    }

    /**
     * 获取课程基础信息
     * @param courseId 课程id
     * @return CourseBase对象
     * @throws RuntimeException
     */
    public CourseBase getCourseBaseById(String courseId) throws RuntimeException{
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 更新课程基础信息
     * @param id 课程id
     * @param courseBase 所需要更新的课程对象
     * @return ResponseResult
     */
    @Transactional
    public ResponseResult updateCourseBase(String id,CourseBase courseBase){
        // 对传入的参数进行合法性判断
        if(StringUtils.isEmpty(id) || courseBase == null || StringUtils.isEmpty(courseBase.getId())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 首先获取旧的对象
        CourseBase oldCoursebase = this.getCourseBaseById(id);
        if(oldCoursebase != null){
            // 更新对象的属性
            oldCoursebase.setName(courseBase.getName());
            oldCoursebase.setMt(courseBase.getMt());
            oldCoursebase.setSt(courseBase.getSt());
            oldCoursebase.setGrade(courseBase.getGrade());
            oldCoursebase.setStudymodel(courseBase.getStudymodel());
            oldCoursebase.setUsers(courseBase.getUsers());
            oldCoursebase.setDescription(courseBase.getDescription());
            // 更新对象
            courseBaseRepository.save(oldCoursebase);
        }else{
            oldCoursebase = new CourseBase();
            BeanUtils.copyProperties(courseBase,oldCoursebase);
            // 不存在的话则新建对象
            this.addCourse(courseBase);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 获取课程营销信息
     * @param courseId 课程id
     * @return CourseMarket对象
     */
    public CourseMarket getCourseMarketById(String courseId){
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 更新课程营销信息
     * @param id 课程id
     * @param courseMarket 所要更新的课程信息
     * @return ResponseResult
     */
    @Transactional
    public ResponseResult updateCourseMarket(String id,CourseMarket courseMarket){
        // 检查参数是否合法
        if(StringUtils.isEmpty(id) || courseMarket == null || StringUtils.isEmpty(courseMarket.getId())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CourseMarket oldCourseMarket = this.getCourseMarketById(id);
        if(oldCourseMarket != null){
            // 将所要更新的内容进行修改，比吗丢失数据
            oldCourseMarket.setCharge(courseMarket.getCharge());
            oldCourseMarket.setStartTime(courseMarket.getStartTime());
            oldCourseMarket.setEndTime(courseMarket.getEndTime());
            oldCourseMarket.setPrice(courseMarket.getPrice());
            oldCourseMarket.setQq(courseMarket.getQq());
            oldCourseMarket.setValid(courseMarket.getValid());
        }else{
            // 不存在则新建对象
            oldCourseMarket = new CourseMarket();
            BeanUtils.copyProperties(courseMarket,oldCourseMarket);
            // 设置课程id
            oldCourseMarket.setId(id);
        }
        // 保存对象
        courseMarketRepository.save(oldCourseMarket);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 添加课程图片信息，将课程信息和图片id进行关联
     * @param courseId 课程id
     * @param pic 图片id
     * @return ResponseResult
     */
    @Transactional
    public ResponseResult addCoursePic(String courseId,String pic){
        // 对参数进行合法性验证
        if(StringUtils.isEmpty(courseId) || StringUtils.isEmpty(pic)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 声明coursepic对象
        CoursePic coursePic = null;
        // 首先查找当前对象是否存在
        Optional<CoursePic> optional = coursepicRepository.findById(courseId);
        if(optional.isPresent()){
            coursePic = optional.get();
        }else{
            // 不存在则新建一个对象
            coursePic = new CoursePic();
        }
        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);
        // 执行更新或保存任务
        coursepicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 根据课程id返回课程对应的图片id
     * @param courseId 课程id
     * @return 课程图片对象
     */
    public CoursePic findCoursePic(String courseId){
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<CoursePic> optional = coursepicRepository.findById(courseId);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 删除课程图片
     * @param courseId 课程id
     * @return 删除结果
     */
    @Transactional
    public ResponseResult deleteCoursePic(String courseId){
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        long result = coursepicRepository.deleteByCourseid(courseId);
        if(result>0){
            return new ResponseResult(CommonCode.SUCCESS);
        }else{
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 返回课程预览所需的数据
     * @param id 课程id
     * @return CourseView对象
     */
    public CourseView courseview(String id) {
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CourseView courseView = new CourseView();
        // 课程基本信息
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if(courseBaseOptional.isPresent()){
            courseView.setCourseBase(courseBaseOptional.get());
        }
        // 课程图片信息
        Optional<CoursePic> coursePicOptional = coursepicRepository.findById(id);
        if(coursePicOptional.isPresent()){
            courseView.setCoursePic(coursePicOptional.get());
        }
        // 课程营销信息
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if(courseMarketOptional.isPresent()){
            courseView.setCourseMarket(courseMarketOptional.get());
        }
        // 课程计划列表
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    /**
     * 获取coursebase对象
     * @param courseId coursebase id
     * @returncoursebase对象
     */
    public CourseBase findCourseBaseById(String courseId){
        if(StringUtils.isEmpty(courseId)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if(courseBaseOptional.isPresent()){
            return courseBaseOptional.get();
        }
        return null;
    }
    /**
     * 课程预览实现
     * @param id 课程id
     * @return 课程预览结果
     */
    public CoursePublishResult preview(String id) {
        CourseBase one = findCourseBaseById(id);//获取页面对象信息
        // 首先创建对应的cmspage对象
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);//站点id
        cmsPage.setTemplateId(publish_templateId);//模版id
        cmsPage.setPageName(id+".html");//页面名称
        cmsPage.setPageAliase(one.getName());//页面别名
        cmsPage.setPageWebPath(publish_page_webpath);//web路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);//物理路径
        cmsPage.setDataUrl(publish_dataUrlPre+id);//data url
        // 远程调用cms服务的保存页面方法
        CmsPageResult cmsPageResult = cmsPageClient.savePage(cmsPage);
        // 将页面信息封装为CoursePublishResult
        CoursePublishResult coursePublishResult = new CoursePublishResult();
        if(!cmsPageResult.isSuccess()){
            return new CoursePublishResult(CommonCode.FAIL,null);
        }
        // 返回结果
        // 页面id
        String pageId = cmsPageResult.getCmsPage().getPageId();
        // 预览url
        String url = previewUrl+pageId;
        return new CoursePublishResult(CommonCode.SUCCESS,url);
    }

    /**
     * 课程发布实现
     * @param id 课程id
     * @return 课程发布结果
     */
    @Transactional
    public CoursePublishResult publish(String id) {
        // 构建cms对象
        CourseBase one = findCourseBaseById(id);//获取页面对象信息
        // 首先创建对应的cmspage对象
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);//站点id
        cmsPage.setTemplateId(publish_templateId);//模版id
        cmsPage.setPageName(id+".html");//页面名称
        cmsPage.setPageAliase(one.getName());//页面别名
        cmsPage.setPageWebPath(publish_page_webpath);//web路径
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);//物理路径
        cmsPage.setDataUrl(publish_dataUrlPre+id);//data url
        // 远程调用 cms 的课程一键发布接口
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        // 更改当前课程的状态
        CourseBase courseBase = saveCoursePubState(id);
        if(courseBase == null){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // 课程索引
        // 创建CoursePub对象
        CoursePub coursePub = createCoursePub(id);
        // 保存CoursePub对象
        CoursePub newCoursePub = saveCoursePub(id,coursePub);
        if(newCoursePub == null){
            // 课程索引创建失败
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // TODO:课程缓存

        // 保存课程媒资信息到课程媒资发布表中
        saveTeachPlanMediaPub(id);
        // 返回课程发布结果
        String pageUrl = cmsPostPageResult.getPageUrl();
        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    /**
     * 保存课程媒资信息到课程媒资发布表中
     * @param id
     */
    private void saveTeachPlanMediaPub(String id) {
        // 查询课程的媒资信息
        List<TeachplanMedia> teachplanMedias = teachplanMediaRepository.findByCourseId(id);
        // 首先删除原先的课程媒资发布信息
        teachplanMediaPubRepository.deleteByCourseId(id);
        // 将课程的媒资信息保存到课程媒资发布表中
        List<TeachplanMediaPub> teachplanMediaPubs = new ArrayList<>();
        for(TeachplanMedia teachplanMedia : teachplanMedias){
            TeachplanMediaPub teachplanMediaPub = new TeachplanMediaPub();
            BeanUtils.copyProperties(teachplanMedia, teachplanMediaPub);
            teachplanMediaPubs.add(teachplanMediaPub);
        }
        // 保存课程媒资发布信息
        teachplanMediaPubRepository.saveAll(teachplanMediaPubs);
    }

    /**
     * 保存CoursePub对象
     * @param id 课程id
     * @param coursePub 所需要保存的CoursePub对象
     * @return 保存之后的CoursePub对象
     */
    private CoursePub saveCoursePub(String id, CoursePub coursePub) {
        // 参数合法性验证
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CoursePub coursePubNew = null;
        // 首先查询该对象是否存在
        Optional<CoursePub> coursePubOptional = coursePubRepository.findById(id);
        if(coursePubOptional.isPresent()){
            coursePubNew = coursePubOptional.get();
        }
        if(coursePubNew == null){
            coursePubNew = new CoursePub();
        }
        // 将传入的对象复制进去,对象存在进行更新，对象不存在则新建
        BeanUtils.copyProperties(coursePub,coursePubNew);
        // 设置主键
        coursePubNew.setId(id);
        // 设置课程发布时间戳，为logstack使用
        coursePubNew.setTimestamp(new Date());
        // 设置课程发布时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY‐MM‐dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        coursePubNew.setPubTime(date);
        // 保存对象
        coursePubRepository.save(coursePubNew);
        return coursePubNew;
    }

    /**
     * 创建CoursePub对象
     * @param id 课程id
     * @return CoursePub对象
     */
    private CoursePub createCoursePub(String id) {
        // 参数合法性验证
        if(StringUtils.isEmpty(id)){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 创建coursepub对象
        CoursePub coursePub = new CoursePub();
        coursePub.setId(id);

        // 根据课程id查询couse_base对象
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(id);
        if(courseBaseOptional.isPresent()){
            CourseBase courseBase = courseBaseOptional.get();
            // 将couse_base对象属性信息拷贝到coursepub对象中
            BeanUtils.copyProperties(courseBase,coursePub);
        }

        // 根据课程id查询couse_market对象
        Optional<CourseMarket> courseMarketOptional = courseMarketRepository.findById(id);
        if(courseMarketOptional.isPresent()){
            CourseMarket courseMarket = courseMarketOptional.get();
            // 将courseMarket对象属性信息拷贝到coursepub对象中
            BeanUtils.copyProperties(courseMarket,coursePub);
        }

        // 根据课程id查询couse_pic对象
        Optional<CoursePic> coursePicOptional = coursepicRepository.findById(id);
        if(coursePicOptional.isPresent()){
            CoursePic coursePic = coursePicOptional.get();
            // 将courseMarket对象属性信息拷贝到course_pic对象中
            BeanUtils.copyProperties(coursePic,coursePub);
        }

        // 查看课程教学计划信息
        TeachplanNode teachplanNode = teachplanMapper.selectList(id);
        // 将课程计划转换为json
        String jsonString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(jsonString);
        return coursePub;
    }

    /**
     * 更改课程状态信息
     * @param id 课程id
     * @return 课程对象
     */
    private CourseBase saveCoursePubState(String id) {
        CourseBase courseBase = this.findCourseBaseById(id);
        courseBase.setStatus("202002");//数据字典中的课程状态
        CourseBase one = courseBaseRepository.save(courseBase);
        return one;
    }

    /**
     * 保存课程计划和媒资文件相关联信息
     * @param teachplanMedia
     * @return
     */
    @Transactional
    public ResponseResult savemedia(TeachplanMedia teachplanMedia) {
        // 首先进行合法性验证
        if(teachplanMedia == null || StringUtils.isEmpty(teachplanMedia.getTeachplanId())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 获取课程计划id
        String teachplanId = teachplanMedia.getTeachplanId();
        // 查询课程计划对象
        Optional<Teachplan> optionalTeachplan = teachplanRepository.findById(teachplanId);
        // 判断课程计划对象是否存在
        if(!optionalTeachplan.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Teachplan teachplan = optionalTeachplan.get();
        // 对课程计划进行判断，只有级别为3的课程计划才可以关联视频信息
        if(teachplan == null || !teachplan.getGrade().equals("3")){
            ExceptionCast.cast(CourseCode.COURSE_MEDIA_TEACHPLAN_GRADEERROR);
        }
        // 查询TeachplanMedia对象
        TeachplanMedia one = null;
        Optional<TeachplanMedia> teachplanMediaOptional = teachplanMediaRepository.findById(teachplanId);
        // 数据库中已经存在TeachplanMedia对象则进行更新，否则执行新建保存
        if(teachplanMediaOptional.isPresent()){
            one = teachplanMediaOptional.get();
        }else{
            one = new TeachplanMedia();
        }
        // 更新TeachplanMedia对象属性
        one.setTeachplanId(teachplanId);
        one.setCourseId(teachplanMedia.getCourseId());
        one.setMediaFileOriginalName(teachplanMedia.getMediaFileOriginalName());
        one.setMediaId(teachplanMedia.getMediaId());
        one.setMediaUrl(teachplanMedia.getMediaUrl());
        teachplanMediaRepository.save(one);
        // 返回结果
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
