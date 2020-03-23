package com.xedu.manage_course.service;

import com.xedu.framework.domain.course.CourseBase;
import com.xedu.framework.domain.course.Teachplan;
import com.xedu.framework.domain.course.ext.TeachplanNode;
import com.xedu.framework.exception.ExceptionCast;
import com.xedu.framework.model.response.CommonCode;
import com.xedu.framework.model.response.ResponseResult;
import com.xedu.manage_course.dao.CourseBaseRepository;
import com.xedu.manage_course.dao.TeachplanMapper;
import com.xedu.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
