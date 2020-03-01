package com.xedu.api.cms;

import com.xedu.framework.domain.cms.request.QueryPageRequest;
import com.xedu.framework.model.response.QueryResponseResult;

/**
 * @Author: Xin Wang.
 * @Description: 页面请求控制接口
 * @Date:Created in 2020/3/1 17:56.
 */
public interface CmsPageControllerApi {
    // 页面查询
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);
}
