package com.xedu.framework.domain.cms.response;

import com.xedu.framework.model.response.ResponseResult;
import com.xedu.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/4/11 10:17.
 * @Description: 课程界面一键发布接口返回值
 */
@Data
@NoArgsConstructor
public class CmsPostPageResult extends ResponseResult {
    String pageUrl;
    public CmsPostPageResult(ResultCode resultCode,String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
