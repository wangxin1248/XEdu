package com.xedu.framework.domain.course.ext;

import com.xedu.framework.domain.course.Teachplan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/2/7.
 * 查询教学计划列表的返回类型
 */
@Data
@ToString
public class TeachplanNode extends Teachplan {

    List<TeachplanNode> children;
    // 媒资信息
    private String mediaId;
    private String mediaFileOriginalName;

}
