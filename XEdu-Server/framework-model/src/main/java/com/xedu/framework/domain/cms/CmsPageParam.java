package com.xedu.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Xin Wang.
 * @Description:
 * @Date:Created in 2020/3/1 10:46.
 */
@Data
@ToString
public class CmsPageParam {
   //参数名称
    private String pageParamName;
    //参数值
    private String pageParamValue;

}
