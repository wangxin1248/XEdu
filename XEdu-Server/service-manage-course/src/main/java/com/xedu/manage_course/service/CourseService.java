package com.xedu.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xedu.framework.domain.course.CourseBase;
import com.xedu.framework.domain.course.CourseMarket;
import com.xedu.framework.domain.course.CoursePic;
import com.xedu.framework.domain.course.Teachplan;
import com.xedu.framework.domain.course.ext.CourseInfo;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.domain.course.request.CourseListRequest;
import com.xedu.framework.domain.course.response.AddCourseResult;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.QueryResponseResult;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.remote.rmi._RMIConnection_Stub;
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
}
