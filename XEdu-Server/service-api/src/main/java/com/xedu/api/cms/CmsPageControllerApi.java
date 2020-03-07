package com.xedu.api.cms;

import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Author: Xin Wang.
 * @Description: 页面请求控制接口
 * @Date:Created in 2020/3/1 17:56.
 */
@Api(value = "cms页面管理接口，提供页面的增删改查操作",tags = {"cms页面管理接口"})
public interface CmsPageControllerApi {
    @ApiOperation("分页查询页面列表接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value = "每页记录数",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}
