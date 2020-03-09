package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.model.response.QueryResult;
import com.xedu.manage_cms.dao.CmsPageRepositoty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: Xin Wang.
 * @Date:Created in 2020/3/7 12:52.
 * @Description: 对 CmsPageRepositoty 接口进行测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositotyTest {

    // 所需要测试的接口对象
    @Autowired
    CmsPageRepositoty cmsPageRepositoty;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepositoty.findAll();
        System.out.println(all);
    }

    @Test
    public void testFindPage(){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepositoty.findAll(pageable);
        // 将查询结果封装到QueryResult中
        QueryResult<CmsPage> result = new QueryResult<>();
        result.setList(all.getContent());
        result.setTotal(all.getTotalElements());
        System.out.println(all);
    }
}
