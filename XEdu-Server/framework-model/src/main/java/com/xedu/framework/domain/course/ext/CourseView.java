package com.xedu.framework.domain.course.ext;

import com.xedu.framework.domain.course.CourseBase;
import com.xedu.framework.domain.course.CourseMarket;
import com.xedu.framework.domain.course.CoursePic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/7 13:11.
 * @Description: 课程预览界面所需的数据信息封装类
 */
@Data
@NoArgsConstructor
@ToString
public class CourseView implements Serializable {
    CourseBase courseBase;//课程基本信息
    CoursePic coursePic;//课程图片信息
    CourseMarket courseMarket;//课程营销信息
    TeachplanNode teachplanNode;//课程教学计划
}
