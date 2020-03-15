package com.xedu.framework.domain.cms.request;

import com.xedu.framework.model.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: Xin Wang.
 * @Description: 请求模型类，表示页面请求的查询条件
 * @Date:Created in 2020/3/1 17:25.
 */
@Data
public class QueryPageRequest extends RequestData {
    // 站点id
    @ApiModelProperty("站点id")
    private String siteId;
    // 页面id
    @ApiModelProperty("页面id")
    private String pageId;
    // 模版id
    @ApiModelProperty("模版id")
    private String TemplateId;
    // 页面名称
    @ApiModelProperty("页面名称")
    private String pageName;
    // 页面别名
    @ApiModelProperty("页面别名")
    private String pageAlias;
    // 页面类型
    @ApiModelProperty("页面类型")
    private String pageType;
}
