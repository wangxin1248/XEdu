package com.xedu.framework.domain.cms;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * @Author: Xin Wang.
 * @Description:
 * @Date:Created in 2020/2/15 10:01.
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    private Map mapValue;
    private String value;

}
