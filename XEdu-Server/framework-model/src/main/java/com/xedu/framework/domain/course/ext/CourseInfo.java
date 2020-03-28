package com.xedu.framework.domain.course.ext;

import com.xedu.framework.domain.course.CourseBase;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;

/**
 * Created by admin on 2018/2/10.
 */
@Data
@ToString
public class CourseInfo extends CourseBase {
    //课程图片
    private String pic;
}
