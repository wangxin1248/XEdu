package com.xedu.framework.domain.cms.request;

import com.xedu.framework.model.request.RequestData;
import lombok.Data;

/**
 * @Author: Xin Wang.
 * @Description: 请求模型类，表示页面请求的查询条件
 * @Date:Created in 2020/3/1 17:25.
 */
@Data
public class QueryPageRequest extends RequestData {
    // 站点id
    private String siteId;
    // 页面id
    private String pageId;
    // 模版id
    private String TemplateId;
    // 页面名称
    private String pageName;
    // 页面别名
    private String pageAlias;
}
