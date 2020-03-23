package com.xedu.manage_course.dao;

import com.xedu.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/22 21:53.
 * @Description: 使用MyBits来完成teachplan表的dao操作
 */
@Mapper
public interface TeachplanMapper {
    public TeachplanNode selectList(String courseId);
}
