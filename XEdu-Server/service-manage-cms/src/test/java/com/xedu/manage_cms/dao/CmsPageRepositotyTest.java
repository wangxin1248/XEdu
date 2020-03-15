package com.xedu.manage_cms.dao;

import com.xedu.framework.domain.cms.CmsPage;
import com.xedu.framework.domain.cms.CmsSite;
import com.xedu.framework.model.response.QueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
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
    CmsPageRepository cmsPageRepository;
    @Autowired
    CmsSiteRepository cmsSiteRepository;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testFindPage(){
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        // 将查询结果封装到QueryResult中
        QueryResult<CmsPage> result = new QueryResult<>();
        result.setList(all.getContent());
        result.setTotal(all.getTotalElements());
        System.out.println(all);
    }

    @Test
    public void testFindBy(){
        // 分页查询
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        // 查询条件
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("轮播");
        // 查询条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());
        // 查询条件实例
        Example<CmsPage> example = Example.of(cmsPage,matcher);
        // 开始进行查询
        Page<CmsPage> all = cmsPageRepository.findAll(example,pageable);
        // 查询结果处理
        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    @Test
    public void testFindSite(){
        // 查询当前的site站点信息
        List<CmsSite> all = cmsSiteRepository.findAll();
        System.out.println(all);
    }
}
